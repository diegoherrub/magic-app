package pol.rubiano.magicapp.features.search.domain.models

import pol.rubiano.magicapp.features.cards.domain.models.Card

data class Scryfall(
    val totalCards: Int?,
    val hasMore: Boolean?,
    val nextPage: String?,
    val data: List<Card>?,
)
