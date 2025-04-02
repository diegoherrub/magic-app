package pol.rubiano.magicapp.features.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import pol.rubiano.magicapp.app.domain.AppError
import pol.rubiano.magicapp.app.domain.UiState
import pol.rubiano.magicapp.databinding.DecksFragmentBinding
import pol.rubiano.magicapp.features.domain.models.Deck
import pol.rubiano.magicapp.features.presentation.adapters.DecksAdapter
import pol.rubiano.magicapp.features.presentation.viewmodels.DecksViewModel

class DecksFragment : Fragment() {

    private var _binding: DecksFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DecksViewModel by viewModel()
    private val args: DecksFragmentArgs by navArgs()

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

        adapter = DecksAdapter()
//        adapter = DecksAdapter(
//            val action = DecksFragmentDirections.actionDecksListFragmentToDeckConfigFragment(deck)
//            findNavController().navigate(action)
////            onDeckClickToDetails = { deck ->
////                onClickDeck(deck)
////            }
//        )
        binding.decksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.decksRecyclerView.adapter = adapter

        viewModel.userDecks.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    val decks = state.data
                    adapter.submitList(decks)
                    binding.decksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                }
                is UiState.Loading -> {}
                is UiState.Empty -> {
                    val action = DecksFragmentDirections.actionDecksFragmentToNewDeckFragment()
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

//    protected abstract fun onClickDeck(deck: Deck)
}

