package pol.rubiano.magicapp.features.presentation.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.gson.Gson
import pol.rubiano.magicapp.databinding.SearchResultsFragmentBinding
import pol.rubiano.magicapp.features.presentation.adapters.SearchResultsAdapter
import pol.rubiano.magicapp.features.presentation.viewmodels.SearchViewModel

class ResultsFragment : Fragment() {

    private var _binding: SearchResultsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModel()
    private lateinit var adapter: SearchResultsAdapter
    private val args: ResultsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchResultsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SearchResultsAdapter { card ->
            // Crea la acción que navega a ViewCardFragment y pasa la carta.
            val cardJson = Gson().toJson(card)
            val action = ResultsFragmentDirections.actionSearchResultsFragmentToViewCardFragment(cardJson)
            findNavController().navigate(action)
        }
        binding.resultsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.resultsRecyclerView.adapter = adapter

        viewModel.cards.observe(
            viewLifecycleOwner, Observer
            { cards ->
                adapter.submitList(cards)
            })

        binding.resultsRecyclerView.addItemDecoration(
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        )

        binding.resultsRecyclerView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                    if (lastVisibleItem >= totalItemCount - 5) {
                        viewModel.loadMoreCards()
                    }
                }
            })

        if (args.query.startsWith("http")) {
            viewModel.fetchSearchPage(args.query)
        } else {
            viewModel.fetchSearchCard(args.query)
        }

        binding.btnNextPage.setOnClickListener {
            val newQuery = viewModel.getNextPageUrl() ?: args.query
            if (newQuery == args.query) return@setOnClickListener
            val action = ResultsFragmentDirections.actionResultsFragmentSelf(newQuery)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
