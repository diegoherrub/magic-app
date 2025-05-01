package pol.rubiano.magicapp.features.decks.domain.models

import pol.rubiano.magicapp.features.cards.domain.models.Card
import pol.rubiano.magicapp.features.cards.domain.models.CardCategory
import pol.rubiano.magicapp.features.decks.data.local.CardInDeckEntity

sealed class DeckConfigItem {
    data class Header(val title: String) : DeckConfigItem()
    data class CardGroup(
        val cards: List<Pair<Card, CardInDeckEntity>>,  // Cambiado a Pair<Card, CardInDeckEntity>
        val category: CardCategory,
        val mainBoardCopies: Int,
        val sideBoardCopies: Int,
        val mayBeBoardCopies: Int
    ) : DeckConfigItem()
}