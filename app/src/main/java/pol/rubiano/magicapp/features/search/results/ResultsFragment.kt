package pol.rubiano.magicapp.features.search.results

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
import pol.rubiano.magicapp.app.presentation.viewmodels.CardsViewModel
import pol.rubiano.magicapp.databinding.SearchResultsFragmentBinding
import pol.rubiano.magicapp.features.decks.DecksViewModel
import pol.rubiano.magicapp.features.search.SearchViewModel

class ResultsFragment : Fragment() {

    private var _binding: SearchResultsFragmentBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModel()
    private val decksViewModel: DecksViewModel by viewModel()
    private val cardsViewModel: CardsViewModel by viewModel()
    private val args: ResultsFragmentArgs by navArgs()
    private val errorFactory: AppErrorUIFactory by inject()
    private var layoutManagerState: Parcelable? = null
    private lateinit var adapter: SearchResultsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchResultsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val deck = args.deck

        adapter = SearchResultsAdapter { card ->
            cardsViewModel.saveCardToLocal(card)
            if (deck != null) {
                decksViewModel.addCardToDeck(deck, card)
            }
            val action = ResultsFragmentDirections
                .actSearchResultsToDeckDetails(deck)
            findNavController().navigate(action)
        }

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.resultsRecyclerView.layoutManager = layoutManager
        binding.resultsRecyclerView.adapter = adapter

        searchViewModel.cards.observe(viewLifecycleOwner, Observer { cards ->
            adapter.submitList(cards)
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

        // Restore scroll position if available
        if (savedInstanceState != null) {
            layoutManagerState = savedInstanceState.getParcelable("layoutManagerState")
            layoutManagerState?.let { state ->
                layoutManager.onRestoreInstanceState(state)
            }
        }

        // Only trigger a new query if no cards are currently loaded
        if (searchViewModel.cards.value.isNullOrEmpty()) {
            if (args.query.startsWith("http")) {
                searchViewModel.fetchSearchPage(args.query)
            } else {
                searchViewModel.fetchSearchCard(args.query)
            }
        }

        // Observe for errors to display the error overlay
        setupObserver()
    }

    private fun setupObserver() {
        searchViewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            bindError(uiState.appError)
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
        // Save RecyclerView scroll state
        layoutManagerState = binding.resultsRecyclerView.layoutManager?.onSaveInstanceState()
        outState.putParcelable("layoutManagerState", layoutManagerState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
