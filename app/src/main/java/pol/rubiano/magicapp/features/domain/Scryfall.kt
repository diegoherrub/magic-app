package pol.rubiano.magicapp.features.domain

import pol.rubiano.magicapp.app.domain.Card

data class Scryfall(
    val totalCards: Int?,
    val hasMore: Boolean?,
    val nextPage: String?,
    val data: List<Card>?,
)
