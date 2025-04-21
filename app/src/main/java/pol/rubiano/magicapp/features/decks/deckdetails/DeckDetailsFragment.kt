package pol.rubiano.magicapp.features.decks.deckdetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.cards.data.mapManaSymbols
import pol.rubiano.magicapp.app.domain.AppError
import pol.rubiano.magicapp.app.domain.DeckStatsAnalyzer
import pol.rubiano.magicapp.app.domain.UiState
import pol.rubiano.magicapp.app.domain.models.Card
import pol.rubiano.magicapp.app.domain.models.CardCategory
import pol.rubiano.magicapp.databinding.DeckDetailsBinding
import pol.rubiano.magicapp.features.domain.models.Deck
import pol.rubiano.magicapp.features.domain.models.DeckConfigItem
import pol.rubiano.magicapp.features.decks.DecksViewModel

class DeckDetailsFragment : Fragment() {

    private var _binding: DeckDetailsBinding? = null
    private val binding get() = _binding!!
    private val decksViewModel: DecksViewModel by viewModel()
    private val deckDetailsFragmentArgs: DeckDetailsFragmentArgs by navArgs()
//    private val resultsFragmentArgs: ResultsFragmentArgs by navArgs()
    private lateinit var currentDeck: Deck
    private lateinit var adapter: DeckDetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DeckDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentDeck = deckDetailsFragmentArgs.deck
//        currentDeck = resultsFragmentArgs.deck
        decksViewModel.loadCurrentDeck(currentDeck)
        setupRecyclerViewCardsOfDeck()
        setupObservers()
    }

    private fun setupRecyclerViewCardsOfDeck() {
        adapter = DeckDetailsAdapter()
        binding.recyclerViewCardsOfDeck.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewCardsOfDeck.adapter = adapter
    }

    private fun setupObservers() {
        decksViewModel.addedCardToDeck.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    // Get the updated deck and update UI elements accordingly
                    val updatedDeck = state.data
                    binding.cfgDeckName.text = updatedDeck.name
                    binding.cfgDeckDescription.text = updatedDeck.description
                    // If you need to update other parts (e.g., refresh your adapter),
                    // you might trigger another call to load the cards
                }
                is UiState.Loading -> {
                    // Optionally show a loading indicator
                }
                is UiState.Error -> {
                    // Optionally show an error message
                }
                is UiState.Empty -> {
                    // Handle empty state if needed
                }
            }
        }


        decksViewModel.currentDeck.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    val deck = state.data
                    binding.cfgDeckName.text = deck.name
                    binding.cfgDeckDescription.text = deck.description
                }

                is UiState.Loading -> {}
                is UiState.Empty -> {}
                is UiState.Error -> {
                    AppError.AppDataError
                }
            }
        }

        decksViewModel.fetchedCardsFromDeck.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    val cards = state.data
                    val grouped = groupCardsByCategory(cards)
                    Log.d("@pol", "Submitting list with ${grouped.size} items")
                    adapter.submitList(grouped)
                    val uniqueColors = DeckStatsAnalyzer.getUniqueColors(cards)
                        .joinToString(" ") { "{$it}" }
                    binding.cfgDeckColors.text = mapManaSymbols(requireContext(), uniqueColors)
                    Log.d("@pol", "cards: ${cards}" )

                }

                is UiState.Loading -> {}
                is UiState.Empty -> {}
                is UiState.Error -> {
                    AppError.AppDataError
                }
            }
        }
    }

    private fun groupCardsByCategory(cards: List<Card?>): List<DeckConfigItem> {
        val groupedItems = mutableListOf<DeckConfigItem>()

        // Creatures
        val creatures = cards.filter { it?.typeLine?.contains("creature", ignoreCase = true) ?: false }
        if (creatures.isNotEmpty()) {
            val creaturesString = getString(R.string.type_creature)
            groupedItems += DeckConfigItem.Header("$creaturesString (${creatures.size})")
            val distinctCreatures = creatures.groupBy { it?.id ?: "" }
            distinctCreatures.forEach { (_, cardList) ->
                groupedItems += DeckConfigItem.CardGroup(cardList, CardCategory.Creatures)
            }
        }

        // Lands
        val lands = cards.filter { it?.typeLine?.contains("land", ignoreCase = true) ?: false }
        if (lands.isNotEmpty()) {
            val landsString = getString(R.string.type_land)
            groupedItems += DeckConfigItem.Header("$landsString (${lands.size})")
            val distinctLands = lands.groupBy { it?.id ?: "" }
            distinctLands.forEach { (_, cardList) ->
                groupedItems += DeckConfigItem.CardGroup(cardList, CardCategory.Lands)
            }
        }

        // Spells (everything else)
        val spells = cards - creatures.toSet() - lands.toSet()
        if (spells.isNotEmpty()) {
            val spellsString = getString(R.string.type_spell)
            groupedItems += DeckConfigItem.Header("$spellsString (${spells.size})")
            val distinctSpells = spells.groupBy { it?.id ?: "" }
            distinctSpells.forEach { (_, cardList) ->
                groupedItems += DeckConfigItem.CardGroup(cardList, CardCategory.Spells)
            }
        }

        Log.d("@pol", "Grouped items: ${groupedItems.joinToString()}")
        return groupedItems
    }

    override fun onResume() {
        super.onResume()
        decksViewModel.loadCurrentDeck(deckDetailsFragmentArgs.deck)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
