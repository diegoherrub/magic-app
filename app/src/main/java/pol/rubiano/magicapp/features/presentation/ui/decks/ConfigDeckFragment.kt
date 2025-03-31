package pol.rubiano.magicapp.features.presentation.ui.decks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.domain.models.Card
import pol.rubiano.magicapp.app.domain.models.CardCategory
import pol.rubiano.magicapp.databinding.DeckFragmentConfigDeckBinding
import pol.rubiano.magicapp.features.domain.models.DeckConfigItem
import pol.rubiano.magicapp.features.presentation.adapters.DeckConfigAdapter
import pol.rubiano.magicapp.features.presentation.adapters.DecksAdapter
import pol.rubiano.magicapp.features.presentation.viewmodels.DecksViewModel

class ConfigDeckFragment : Fragment() {

    private var _binding: DeckFragmentConfigDeckBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DecksViewModel by viewModel()
    private val args: ConfigDeckFragmentArgs by navArgs()
    private lateinit var deckId: String
//    private val deckId: String by lazy { args.deckId }
    private lateinit var adapter: DeckConfigAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DeckFragmentConfigDeckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deckId = args.deckId
        viewModel.loadDeckCards(deckId)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = DeckConfigAdapter { category ->
            val action = ConfigDeckFragmentDirections
                .actionDeckConfigFragmentToSearchFragment(deckId)
            findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupObservers() {
        viewModel.deckCards.observe(viewLifecycleOwner) { cards ->
            val grouped = groupCardsByCategory(cards)
            adapter.submitList(grouped)
        }
    }

    fun groupCardsByCategory(cards: List<Card>): List<DeckConfigItem> {
        val groupedItems = mutableListOf<DeckConfigItem>()

        val creatures = cards.filter { it.typeLine?.contains("creature", ignoreCase = true) ?: false }
        val lands = cards.filter { it.typeLine?.contains("land", ignoreCase = true) ?: false }
        val spells = cards - creatures - lands

        groupedItems += DeckConfigItem.Header("${R.string.type_creature} (${creatures.size})")
        groupedItems += DeckConfigItem.CardGroup(creatures, CardCategory.Creatures)

        groupedItems += DeckConfigItem.Header("${R.string.type_land} (${lands.size})")
        groupedItems += DeckConfigItem.CardGroup(lands, CardCategory.Lands)

        groupedItems += DeckConfigItem.Header("${R.string.type_spell} (${spells.size})")
        groupedItems += DeckConfigItem.CardGroup(spells, CardCategory.Spells)

        return groupedItems
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
