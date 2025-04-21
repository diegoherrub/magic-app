package pol.rubiano.magicapp.features.decks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import pol.rubiano.magicapp.app.domain.AppError
import pol.rubiano.magicapp.app.domain.UiState
import pol.rubiano.magicapp.databinding.DecksFragmentBinding

class DecksFragment : Fragment() {

    private var _binding: DecksFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DecksViewModel by viewModel()

    private lateinit var adapter: DecksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DecksFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DecksAdapter { deck ->
            val action = DecksFragmentDirections.actFromDecksFragmentToDeckDetailsFragment(deck)
            findNavController().navigate(action)
        }
        binding.decksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.decksRecyclerView.adapter = adapter

        setupObserver()
    }

    private fun setupObserver() {
        viewModel.userDecks.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    val decks = state.data
                    adapter.submitList(decks)
                }

                is UiState.Empty -> {
                    val action = DecksFragmentDirections.actFromDecksFragmentToNewDeckFragment()
                    findNavController().navigate(action)
                }

                is UiState.Error -> {
                    AppError.AppDataError
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadUserDecks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

