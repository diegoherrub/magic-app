package pol.rubiano.magicapp.features.search.presentation

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.common.extensions.gone
import pol.rubiano.magicapp.app.common.extensions.visible
import pol.rubiano.magicapp.app.domain.AppError
import pol.rubiano.magicapp.app.presentation.error.AppErrorUIFactory
import pol.rubiano.magicapp.app.domain.UiState
import pol.rubiano.magicapp.databinding.SearchResultsFragmentBinding
import pol.rubiano.magicapp.features.cards.presentation.CardViewModel
import pol.rubiano.magicapp.features.domain.models.Deck
import pol.rubiano.magicapp.features.search.presentation.assets.SearchResultsAdapter
import pol.rubiano.magicapp.features.search.presentation.assets.SearchViewModel


class ResultsFragment : Fragment() {

    private var _binding: SearchResultsFragmentBinding? = null
    private val binding get() = _binding!!
    private val resultsFragmentArgs: ResultsFragmentArgs by navArgs()

    private val searchViewModel: SearchViewModel by viewModel()

    //    private val decksViewModel: DecksViewModel by viewModel()
    private val cardViewModel: CardViewModel by viewModel()
//    private val collectionsViewModel: CollectionsViewModel by viewModel()

    private var layoutManagerState: Parcelable? = null
    private var collectionName: String? = null
    private var deck: Deck? = null
    private var query: String? = null

    private val errorFactory: AppErrorUIFactory by inject()

    private lateinit var adapter: SearchResultsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = SearchResultsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupView(savedInstanceState)
        setupObserver()
    }

    private fun setupToolbar() {
        val toolbar = requireActivity().findViewById<MaterialToolbar>(R.id.toolbar)
        resultsFragmentArgs.collectionName?.let { collectionName = it }
        resultsFragmentArgs.deck?.let { deck = it }
        resultsFragmentArgs.query?.let { query = it }

        toolbar.setNavigationOnClickListener {
            toolbar.setNavigationIcon(R.drawable.back)
            findNavController().navigate(
                ResultsFragmentDirections.actFromSearchResultsFragmentToSearchFragment(
                    collectionName = collectionName,
                    deck = deck
                )
            )
        }
//        if (deck != null || collectionName != null) {
//            toolbar.setNavigationOnClickListener {
//                when {
//                    deck != null -> {
//                        findNavController().navigate(
//                            ResultsFragmentDirections.actFromSearchResultsToDeckDetails(deck)
//                        )
//                    }
//
//                    collectionName != null -> {
//                        findNavController().navigate(
//                            ResultsFragmentDirections.actFromResultsFragmentToCollectionPanel(
//                                collectionName
//                            )
//                        )
//                    }
//
//                    else -> {
//                        toolbar.setNavigationIcon(R.drawable.back)
//                        findNavController().navigate(
//                            ResultsFragmentDirections.actFromSearchResultsFragmentToSearchFragment(
////                                collectionName = collectionName,
////                                deck = deck
//                            )
//                        )
//                    }
//                }
//            }
//        } else {
//            toolbar.navigationIcon = null
//        }
    }

    private fun setupView(savedInstanceState: Bundle?) {
        adapter = SearchResultsAdapter { card ->
            when {
                deck != null -> {}

                collectionName != null -> {}

                else -> {
                    findNavController().navigate(
                        ResultsFragmentDirections.actFromResultsFragmentToCardFragmentView(
                            card,
                            deckId = null,
                            collectionName = null
                        )
                    )
                }
            }
        }

        binding.resultsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = this@ResultsFragment.adapter
        }

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
            val layoutManager = LinearLayoutManager(requireContext())
            layoutManagerState =
                savedInstanceState.getParcelable("layoutManagerState", Parcelable::class.java)
            layoutManagerState?.let { state ->
                layoutManager.onRestoreInstanceState(state)
            }
        }

        query?.let {
            if (it.startsWith("http")) searchViewModel.fetchSearchPage(it)
            else searchViewModel.fetchSearchCard(it)
        }
    }

    private fun setupObserver() {
        cardViewModel.card.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UiState.Error -> bindError(uiState.error)
                else -> {}
            }
        }

        searchViewModel.cards.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {

                is UiState.Success -> {
                    adapter.submitList(uiState.data)
                }

                is UiState.Error -> bindError(uiState.error)
                else -> {
                    adapter.submitList(emptyList())
                }
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
        _binding?.let { binding ->
            layoutManagerState = binding.resultsRecyclerView.layoutManager?.onSaveInstanceState()
            outState.putParcelable("layoutManagerState", layoutManagerState)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
