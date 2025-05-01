package pol.rubiano.magicapp.features.collections.domain.models

data class Collection(
    var name: String,
    val order: Int,
    val cards: List<CardInCollection> = mutableListOf()
)