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

                if (lastVisibleItem >= totalItemCount - 5) {
                    // Para la paginación automática (opcional)
                    viewModel.loadMoreCards()
                }
            }
        })

        // Si el argumento recibido es una URL (inicia con "http"), se invoca fetchSearchPage.
        // Si no, se trata de la búsqueda inicial.
        if (args.query.startsWith("http")) {
            viewModel.fetchSearchPage(args.query)
        } else {
            viewModel.fetchSearchCard(args.query)
        }

        // Listener para el botón "Next Page"
        binding.btnNextPage.setOnClickListener {
            // Se obtiene la URL para la siguiente página desde el ViewModel
            val newQuery = viewModel.getNextPageUrl() ?: args.query
            // Si no hay cambio (por ejemplo, no existe siguiente página), no hacemos nada.
            if (newQuery == args.query) return@setOnClickListener
            // Navegar al mismo fragmento pasando la nueva URL (o query) como argumento.
            val action = ResultsFragmentDirections.actionResultsFragmentSelf(newQuery)
            findNavController().navigate(action)
        }
    }


    //override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    //    super.onViewCreated(view, savedInstanceState)
//
    //    adapter = SearchResultsAdapter()
    //    binding.resultsRecyclerView.layoutManager =
    //        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    //    binding.resultsRecyclerView.adapter = adapter
//
    //    viewModel.cards.observe(viewLifecycleOwner, Observer { cards ->
    //        adapter.submitList(cards)
    //    })
//
    //    binding.resultsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
    //        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
    //            super.onScrolled(recyclerView, dx, dy)
    //            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
    //            val totalItemCount = layoutManager.itemCount
    //            val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
//
    //            if (lastVisibleItem >= totalItemCount - 5) {
    //                viewModel.loadMoreCards()  // Llamar a la paginación
    //            }
    //        }
    //    })
    //
    //    viewModel.fetchSearchCard(args.query)
    //}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
