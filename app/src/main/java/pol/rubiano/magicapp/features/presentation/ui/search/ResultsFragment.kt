package pol.rubiano.magicapp.features.presentation.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.navArgs
import pol.rubiano.magicapp.databinding.SearchResultsFragmentBinding
import pol.rubiano.magicapp.features.presentation.adapters.SearchResultsAdapter
import pol.rubiano.magicapp.features.presentation.viewmodels.SearchViewModel

class ResultsFragment : Fragment() {

    private var _binding: SearchResultsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModel()
    private lateinit var adapter: SearchResultsAdapter

    private val args: ResultsFragmentArgs by navArgs()  // Recibir argumentos

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchResultsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SearchResultsAdapter()
        binding.resultsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.resultsRecyclerView.adapter = adapter

        viewModel.cards.observe(viewLifecycleOwner, Observer { cards ->
            adapter.submitList(cards)
        })

        binding.resultsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

//                if (lastVisibleItem >= totalItemCount - 5) {
//                    viewModel.loadMoreCards()  // Llamar a la paginación
//                }
            }
        })

        // Ejecutar búsqueda con la query recibida
        viewModel.fetchSearchCard(args.query)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
