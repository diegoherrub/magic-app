package pol.rubiano.magicapp.features.domain.models

import pol.rubiano.magicapp.app.domain.models.Card
import pol.rubiano.magicapp.app.domain.models.CardCategory

sealed class DeckConfigItem {
    data class Header(val title: String) : DeckConfigItem()
    data class CardGroup(val cards: List<Card>, val category: CardCategory) : DeckConfigItem()
}
