package pol.rubiano.magicapp.features.search.results

import android.util.Log
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import pol.rubiano.magicapp.app.common.extensions.gone
import pol.rubiano.magicapp.app.common.extensions.visible
import pol.rubiano.magicapp.app.domain.AppError
import pol.rubiano.magicapp.app.presentation.error.AppErrorUIFactory
import pol.rubiano.magicapp.app.cards.presentation.CardsViewModel
import pol.rubiano.magicapp.app.domain.UiState
import pol.rubiano.magicapp.databinding.SearchResultsFragmentBinding
import pol.rubiano.magicapp.features.collections.presentation.assets.CollectionsViewModel
import pol.rubiano.magicapp.features.decks.DecksViewModel
import pol.rubiano.magicapp.features.domain.models.Deck
import pol.rubiano.magicapp.features.search.SearchViewModel

class ResultsFragment : Fragment() {

    private var _binding: SearchResultsFragmentBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModel()
    private val decksViewModel: DecksViewModel by viewModel()
    private val cardsViewModel: CardsViewModel by viewModel()
    private val collectionsViewModel: CollectionsViewModel by viewModel()
    private val args: ResultsFragmentArgs by navArgs()
    private val errorFactory: AppErrorUIFactory by inject()
    private var layoutManagerState: Parcelable? = null
    private lateinit var adapter: SearchResultsAdapter

    private var deck: Deck? = null
    private var collectionName: String? = null
    private var query: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchResultsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deck = args.deck
        Log.d("@pol", "ResultsFragment recibe el deck: $deck")

        collectionName = args.collectionName
        Log.d("@pol", "ResultsFragment recibe el nombre la colección: $collectionName")

        query = args.query
        Log.d("@pol", "ResultsFragment recibe la query: $query")


        adapter = SearchResultsAdapter { card ->
            cardsViewModel.saveCardToLocal(card)

            when {
                deck != null -> {
                    decksViewModel.addCardToDeck(deck!!, card)
                    val action = ResultsFragmentDirections.actFromSearchResultsToDeckDetails(deck)
                    findNavController().navigate(action)
                }
                collectionName != null -> {
                    // Si hay un nombre de colección, realiza una acción específica
                    collectionsViewModel.addCardToCollection(collectionName!!, card.id)
                    Log.d("@pol", "ha guardado la carta ${card.name} en la colección $collectionName")
                    val action = ResultsFragmentDirections.actFromResultsFragmentToCollectionPanel(collectionName)
                    Log.d("@pol", "pasa el nombre de la colección: $collectionName de nuevo al panel de la colección")
                    findNavController().navigate(action)
                }
                else -> {
                    // Acción por defecto, como mostrar detalles de la carta
                    //val action = ResultsFragmentDirections.actionSearchResultsFragmentToViewCardFragment(card.toJson())
                    //findNavController().navigate(action)
                }
            }
//            cardsViewModel.saveCardToLocal(card)
//            if (deck != null) {
//                decksViewModel.addCardToDeck(deck, card)
//            }
//            val action = ResultsFragmentDirections
//                .actSearchResultsToDeckDetails(deck)
//            findNavController().navigate(action)
        }

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.resultsRecyclerView.layoutManager = layoutManager
        binding.resultsRecyclerView.adapter = adapter

        searchViewModel.cards.observe(viewLifecycleOwner, Observer { uiState ->
            if (uiState is UiState.Success) {
                adapter.submitList(uiState.data)
            } else {
                adapter.submitList(emptyList())
            }
        })
        binding.resultsRecyclerView.addItemDecoration(
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        )
        binding.resultsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (lastVisibleItem >= totalItemCount - 5) {
                    searchViewModel.loadMoreCards()
                }
            }
        })

        if (savedInstanceState != null) {
            layoutManagerState = savedInstanceState.getParcelable("layoutManagerState")
            layoutManagerState?.let { state ->
                layoutManager.onRestoreInstanceState(state)
            }
        }

        val currentState = searchViewModel.cards.value
        if (currentState is UiState.Success && currentState.data.isEmpty()) {
            if (query!!.startsWith("http")) {
                args.query?.let { searchViewModel.fetchSearchPage(it) }
            } else {
                args.query?.let { searchViewModel.fetchSearchCard(it) }
            }
        }
        Log.d("@pol", "ResultsFragment() -> query: $query")

        setupObserver()
    }

    private fun setupObserver() {
        cardsViewModel.savedCardToLocal.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UiState.Error -> bindError(uiState.error)
                else -> {}
            }
        }

        searchViewModel.cards.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UiState.Error -> bindError(uiState.error)
                else -> {}
            }
        }
    }

    private fun bindError(appError: AppError?) {
        if (appError != null) {
            binding.resultsRecyclerView.gone()
            binding.appErrorViewContainer.visible()
            binding.appErrorViewContainer.render(errorFactory.build(appError))
            binding.appErrorViewContainer.setOnRetryClickListener {
                binding.appErrorViewContainer.gone()
                binding.resultsRecyclerView.visible()
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(2000)
                    searchViewModel.lastQueryAttempt?.let { lastQuery ->
                        if (lastQuery.startsWith("http")) {
                            searchViewModel.fetchSearchPage(lastQuery)
                        } else {
                            searchViewModel.fetchSearchCard(lastQuery)
                        }
                    }
                }
            }
        } else {
            binding.resultsRecyclerView.visible()
            binding.appErrorViewContainer.gone()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        layoutManagerState = binding.resultsRecyclerView.layoutManager?.onSaveInstanceState()
        outState.putParcelable("layoutManagerState", layoutManagerState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
