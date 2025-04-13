package pol.rubiano.magicapp.features.collections.domain

data class Collection(
    val name: String,
    val cards: List<CardInCollection>
)