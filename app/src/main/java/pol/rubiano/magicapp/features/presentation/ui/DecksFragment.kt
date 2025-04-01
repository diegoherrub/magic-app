package pol.rubiano.magicapp.features.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import pol.rubiano.magicapp.databinding.DecksFragmentBinding
import pol.rubiano.magicapp.features.presentation.adapters.DecksAdapter
import pol.rubiano.magicapp.features.presentation.viewmodels.DecksViewModel

abstract class DecksFragment : Fragment() {

    private var _binding: DecksFragmentBinding? = null
    private val binding get() = _binding!!
    private val decksViewModel: DecksViewModel by viewModel()

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

        adapter = DecksAdapter(
            // asign the navigation's action at the deck pressed to the config fragment
            onDeckClickToConfig = { deck ->
                onClickCard(deck.id)
            }
        )
        binding.decksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.decksRecyclerView.adapter = adapter

        decksViewModel.uiState.observe(viewLifecycleOwner) { state ->
            val decks = state?.decks
            if (decks != null) {
                if (decks.isEmpty()) {
                    findNavController().navigate(
                        DecksFragmentDirections.actionDecksFragmentToNewDeckFragment()
                    )
                } else {
                    adapter.submitList(decks)
                }
            }
        }


    }

    override fun onResume() {
        super.onResume()
        decksViewModel.loadDecks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected abstract fun onClickCard(deckId: String)
}

