package pol.rubiano.magicapp.features.domain.models

import pol.rubiano.magicapp.app.domain.models.Card

data class Scryfall(
    val totalCards: Int?,
    val hasMore: Boolean?,
    val nextPage: String?,
    val data: List<Card>?,
)
