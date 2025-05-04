package pol.rubiano.magicapp.features.decks.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.common.extensions.gone
import pol.rubiano.magicapp.app.common.extensions.visible
import pol.rubiano.magicapp.app.common.utils.mapManaSymbols
import pol.rubiano.magicapp.app.domain.AppError
import pol.rubiano.magicapp.features.decks.domain.assets.DeckStatsAnalyzer
import pol.rubiano.magicapp.app.domain.UiState
import pol.rubiano.magicapp.app.presentation.error.AppErrorUIFactory
import pol.rubiano.magicapp.databinding.DeckPanelBinding
import pol.rubiano.magicapp.features.cards.domain.models.Card
import pol.rubiano.magicapp.features.cards.domain.models.CardCategory
import pol.rubiano.magicapp.features.decks.data.local.CardInDeckEntity
import pol.rubiano.magicapp.features.decks.data.local.toEntity
import pol.rubiano.magicapp.features.decks.presentation.assets.DeckPanelAdapter
import pol.rubiano.magicapp.features.decks.domain.models.DeckConfigItem
import pol.rubiano.magicapp.features.decks.presentation.assets.DecksViewModel

class DeckPanel : Fragment() {

    private var _binding: DeckPanelBinding? = null
    private val binding get() = _binding!!
    private val decksViewModel: DecksViewModel by viewModel()
    private val deckPanelArgs: DeckPanelArgs by navArgs()
    private val errorFactory: AppErrorUIFactory by inject()

    private lateinit var toolbar: MaterialToolbar
    private lateinit var deckId: String
    private lateinit var adapter: DeckPanelAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DeckPanelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupView()
        setupObservers()
    }

    private fun setupToolbar() {
        toolbar = requireActivity().findViewById(R.id.toolbar)
        deckPanelArgs.deckId?.let { deckId = it }
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.itm_addCardToDeck -> {
                    findNavController().navigate(
                        DeckPanelDirections.actFromDeckPanelToSearchFragment(
                            collectionName = null,
                            deckId = deckId,
                        )
                    )
                    true
                }

                else -> false
            }
        }
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(
                DeckPanelDirections.actFromDeckPanelToDecksList()
            )
        }
    }

    private fun setupView() {
        adapter = DeckPanelAdapter()
        binding.deckCardsList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@DeckPanel.adapter
        }
        decksViewModel.getDeckById(deckId)
    }

    private fun setupObservers() {
        decksViewModel.fetchedDeck.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    val deck = state.data
                    toolbar.title = deck.name + " Panel"
                    binding.deckPanelName.text = deck.name
                    binding.deckPanelDescription.text = deck.description
                }

                is UiState.Empty -> {
                    findNavController().navigate(
                        DeckPanelDirections.actFromDeckPanelToDecksList()
                    )
                    Toast.makeText(
                        requireContext(),
                        R.string.str_noDeckWithThatName,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is UiState.Error -> bindError(state.error)
                else -> {}
            }
        }

        decksViewModel.savedCardInDeck.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    val updatedDeck = state.data
                    binding.deckPanelName.text = updatedDeck.name
                    binding.deckPanelDescription.text = updatedDeck.description
                }

                else -> {}
            }
        }

        decksViewModel.fetchedCardsFromDeck.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    val cards = state.data
                    val grouped = groupCardsByCategory(
                        cards.filterNotNull().map { card ->
                            Pair(card, CardInDeckEntity(
                                card.id, deckId, 0, 0, 0
                            ))
                        }
                    )
                    Log.d("@pol", "Submitting list with ${grouped.size} items")
                    adapter.submitList(grouped)
                    val uniqueColors = DeckStatsAnalyzer.getUniqueColors(cards)
                        .joinToString(" ") { "{$it}" }
                    binding.deckPanelColors.text = mapManaSymbols(requireContext(), uniqueColors)
                    Log.d("@pol", "cards: ${cards}")
                }

                is UiState.Loading -> {}
                is UiState.Empty -> {}
                is UiState.Error -> {
                    AppError.AppDataError
                }
            }
        }
        decksViewModel.cardsInDeck.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    val cardsWithData = state.data
                    val grouped = groupCardsByCategory(
                        cardsWithData.map { Pair(it.first, it.second.toEntity()) }
                    )
                    adapter.submitList(grouped)

                    val mainBoardCards = cardsWithData.filter { it.second.mainBoardCopies > 0 }.map { it.first }
                    val uniqueColors = DeckStatsAnalyzer.getUniqueColors(mainBoardCards)
                        .joinToString(" ") { "{$it}" }
                    binding.deckPanelColors.text = mapManaSymbols(requireContext(), uniqueColors)
                }
                else -> {}
            }
        }
    }

    private fun groupCardsByCategory(cards: List<Pair<Card, CardInDeckEntity>>): List<DeckConfigItem> {
        val groupedItems = mutableListOf<DeckConfigItem>()

        val creatures = cards.filter { it.first.typeLine?.contains("creature", ignoreCase = true) ?: false }
        if (creatures.isNotEmpty()) {
            val creaturesString = getString(R.string.type_creature)
            groupedItems += DeckConfigItem.Header("$creaturesString (${creatures.size})")
            val distinctCreatures = creatures.groupBy { it.first.id }
            distinctCreatures.forEach { (_, cardList) ->
                val mainCopies = cardList.sumOf { it.second.mainBoardCopies }
                val sideCopies = cardList.sumOf { it.second.sideBoardCopies }
                val maybeCopies = cardList.sumOf { it.second.mayBeBoardCopies }

                groupedItems += DeckConfigItem.CardGroup(
                    cardList,
                    CardCategory.Creatures,
                    mainCopies,
                    sideCopies,
                    maybeCopies
                )
            }
        }

        val lands = cards.filter { it.first.typeLine?.contains("land", ignoreCase = true) ?: false }
        if (lands.isNotEmpty()) {
            val landsString = getString(R.string.type_land)
            groupedItems += DeckConfigItem.Header("$landsString (${lands.size})")
            val distinctLands = lands.groupBy { it.first.id }
            distinctLands.forEach { (_, cardList) ->
                val mainCopies = cardList.sumOf { it.second.mainBoardCopies }
                val sideCopies = cardList.sumOf { it.second.sideBoardCopies }
                val maybeCopies = cardList.sumOf { it.second.mayBeBoardCopies }

                groupedItems += DeckConfigItem.CardGroup(
                    cardList,
                    CardCategory.Lands,
                    mainCopies,
                    sideCopies,
                    maybeCopies
                )
            }
        }

        val spells = cards - creatures.toSet() - lands.toSet()
        if (spells.isNotEmpty()) {
            val spellsString = getString(R.string.type_spell)
            groupedItems += DeckConfigItem.Header("$spellsString (${spells.size})")
            val distinctSpells = spells.groupBy { it.first.id }
            distinctSpells.forEach { (_, cardList) ->
                val mainCopies = cardList.sumOf { it.second.mainBoardCopies }
                val sideCopies = cardList.sumOf { it.second.sideBoardCopies }
                val maybeCopies = cardList.sumOf { it.second.mayBeBoardCopies }

                groupedItems += DeckConfigItem.CardGroup(
                    cardList,
                    CardCategory.Spells,
                    mainCopies,
                    sideCopies,
                    maybeCopies
                )
            }
        }

        return groupedItems
    }

    private fun bindError(appError: AppError?) {
        if (appError != null) {
            binding.deckPanelContainer.gone()
            binding.appErrorViewContainer.visible()
            binding.appErrorViewContainer.render(errorFactory.build(appError))
            binding.appErrorViewContainer.setOnRetryClickListener {
                binding.appErrorViewContainer.gone()
                binding.deckPanelContainer.visible()
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(2000)
                    decksViewModel.getDeckById(deckId)
                }
            }
        } else {
            binding.deckPanelContainer.visible()
            binding.appErrorViewContainer.gone()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
