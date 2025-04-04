package pol.rubiano.magicapp.features.decks.deckdetails

import android.os.Bundle
import android.util.Log
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
import pol.rubiano.magicapp.databinding.DeckDetailsBinding
import pol.rubiano.magicapp.features.domain.models.Deck
import pol.rubiano.magicapp.features.domain.models.DeckConfigItem
import pol.rubiano.magicapp.features.decks.DecksViewModel

class DeckDetailsFragment : Fragment() {

    private var _binding: DeckDetailsBinding? = null
    private val binding get() = _binding!!
    private val decksViewModel: DecksViewModel by viewModel()
    private val args: DeckDetailsFragmentArgs by navArgs()

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
        currentDeck = args.deck
        decksViewModel.loadCurrentDeck(currentDeck)
        setupRecyclerViewCardsOfDeck()
        setupObservers()
    }

    private fun setupRecyclerViewCardsOfDeck() {
        // Navega al buscador para añadir cartas
        adapter = DeckDetailsAdapter { category ->
            val action = DeckDetailsFragmentDirections.actionDeckDetailsToSearch(currentDeck)
            findNavController().navigate(action)
        }
        binding.recyclerViewCardsOfDeck.adapter = adapter
        binding.recyclerViewCardsOfDeck.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupObservers() {
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

    private fun groupCardsByCategory(cards: List<Card>): List<DeckConfigItem> {
        val groupedItems = mutableListOf<DeckConfigItem>()
        val creatures =
            cards.filter { it.typeLine?.contains("creature", ignoreCase = true) ?: false }
        val lands = cards.filter { it.typeLine?.contains("land", ignoreCase = true) ?: false }
        val spells = cards - creatures.toSet() - lands.toSet()
        val creaturesString = getString(R.string.type_creature)
        val landsString = getString(R.string.type_land)
        val spellsString = getString(R.string.type_spell)
        groupedItems += DeckConfigItem.Header("$creaturesString (${creatures.size})")
        groupedItems += DeckConfigItem.CardGroup(creatures, CardCategory.Creatures)
        groupedItems += DeckConfigItem.Header("$landsString (${lands.size})")
        groupedItems += DeckConfigItem.CardGroup(lands, CardCategory.Lands)
        groupedItems += DeckConfigItem.Header("$spellsString (${spells.size})")
        groupedItems += DeckConfigItem.CardGroup(spells, CardCategory.Spells)
        Log.d("@pol", "Grouped items: ${groupedItems.joinToString()}")
        return groupedItems
    }

//    override fun onResume() {
//        super.onResume()
//        viewModel.loadCurrentDeck(args.deck)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
