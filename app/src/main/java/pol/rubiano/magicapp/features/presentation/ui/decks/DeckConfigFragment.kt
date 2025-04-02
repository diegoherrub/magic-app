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
import pol.rubiano.magicapp.app.data.mapManaSymbols
import pol.rubiano.magicapp.app.domain.AppError
import pol.rubiano.magicapp.app.domain.DeckStatsAnalyzer
import pol.rubiano.magicapp.app.domain.UiState
import pol.rubiano.magicapp.app.domain.models.Card
import pol.rubiano.magicapp.app.domain.models.CardCategory
import pol.rubiano.magicapp.databinding.DeckFragmentConfigDeckBinding
import pol.rubiano.magicapp.features.domain.models.Deck
import pol.rubiano.magicapp.features.domain.models.DeckConfigItem
import pol.rubiano.magicapp.features.presentation.adapters.DeckConfigAdapter
import pol.rubiano.magicapp.features.presentation.ui.decks.DeckConfigFragmentDirections.Companion.actionDeckConfigFragmentToSearchFragment
import pol.rubiano.magicapp.features.presentation.viewmodels.DecksViewModel

class DeckConfigFragment : Fragment() {

    private var _binding: DeckFragmentConfigDeckBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DecksViewModel by viewModel()
    private val args: DeckConfigFragmentArgs by navArgs()

    private lateinit var adapter: DeckConfigAdapter
    private lateinit var receivedDeck: Deck

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DeckFragmentConfigDeckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        receivedDeck = args.deck
        viewModel.loadDeckCards(receivedDeck)
        setupRecyclerViewCardsOfDeck()
        setupObservers()
    }

    private fun setupRecyclerViewCardsOfDeck() {
//        adapter = DeckConfigAdapter { category ->
//            val action = DeckConfigFragmentArgs
//                .actionDeckConfigFragmentToSearchFragment(receivedDeck)
//            findNavController().navigate(action)
//        }
        binding.recyclerViewCardsOfDeck.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewCardsOfDeck.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.addedDeck.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    val deck = state.data
                    binding.cfgDeckName.text = deck.name
                    binding.cfgDeckDescription.text = deck.description
                }

                is UiState.Loading -> {}
                is UiState.Empty -> {}
                is UiState.Error -> AppError.AppDataError
            }
        }

        viewModel.fetchedCardsFromDeck.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    val cards = state.data
                    val grouped = groupCardsByCategory(cards)
                    adapter.submitList(grouped)
                    val uniqueColors = DeckStatsAnalyzer.getUniqueColors(cards)
                        .joinToString(" ") { "{$it}" }
                    binding.cfgDeckColors.text = mapManaSymbols(requireContext(), uniqueColors)
                }

                is UiState.Loading -> {}
                is UiState.Empty -> {}
                is UiState.Error -> AppError.AppDataError
            }
        }
    }

    private fun groupCardsByCategory(cards: List<Card>): List<DeckConfigItem> {
        val groupedItems = mutableListOf<DeckConfigItem>()
        val creatures =
            cards.filter { it.typeLine?.contains("creature", ignoreCase = true) ?: false }
        val lands = cards.filter { it.typeLine?.contains("land", ignoreCase = true) ?: false }
        val spells = cards - creatures.toSet() - lands.toSet()
//        val spells = cards - creatures - lands
        val creaturesString = (R.string.type_creature).toString()
        val landsString = (R.string.type_land).toString()
        val spellsString = (R.string.type_spell).toString()
        groupedItems += DeckConfigItem.Header("$creaturesString (${creatures.size})")
        groupedItems += DeckConfigItem.CardGroup(creatures, CardCategory.Creatures)
        groupedItems += DeckConfigItem.Header("$landsString (${lands.size})")
        groupedItems += DeckConfigItem.CardGroup(lands, CardCategory.Lands)
        groupedItems += DeckConfigItem.Header("$spellsString (${spells.size})")
        groupedItems += DeckConfigItem.CardGroup(spells, CardCategory.Spells)
        return groupedItems
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
