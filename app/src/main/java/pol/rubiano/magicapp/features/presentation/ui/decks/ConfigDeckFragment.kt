package pol.rubiano.magicapp.features.presentation.ui.decks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.lifecycleScope
import pol.rubiano.magicapp.databinding.DeckFragmentConfigDeckBinding
import pol.rubiano.magicapp.features.domain.entities.Deck
import pol.rubiano.magicapp.features.presentation.viewmodels.DeckViewModel

class ConfigDeckFragment : Fragment() {

    private var _binding: DeckFragmentConfigDeckBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DeckViewModel by viewModel()
    private val args: ConfigDeckFragmentArgs by navArgs()

    private lateinit var currentDeck: Deck

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

        lifecycleScope.launch {
            val deck = viewModel.getDeckById(deckId)
            if (deck != null) {
                currentDeck = deck
                binding.cfgDeckName.setText(deck.name)
                binding.cfgDeckDescription.setText(deck.description)
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
