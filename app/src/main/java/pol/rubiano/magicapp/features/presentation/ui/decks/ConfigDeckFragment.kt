package pol.rubiano.magicapp.features.presentation.ui.decks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import pol.rubiano.magicapp.databinding.DeckFragmentConfigDeckBinding
import pol.rubiano.magicapp.features.presentation.viewmodels.DecksViewModel

class ConfigDeckFragment : Fragment() {

    private var _binding: DeckFragmentConfigDeckBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DecksViewModel by viewModel()

    // retrieve the deckId from the arguments
    private val args: ConfigDeckFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DeckFragmentConfigDeckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val deckId = args.deckId
        viewModel.loadDeckById(deckId)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.selectedDeck.observe(viewLifecycleOwner) { deck ->
            deck?.let {
                binding.cfgDeckName.text = it.name
                binding.cfgDeckDescription.text = it.description
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
