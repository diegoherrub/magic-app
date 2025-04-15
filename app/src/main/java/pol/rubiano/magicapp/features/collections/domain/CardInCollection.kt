package pol.rubiano.magicapp.features.collections.domain

data class CardInCollection(
    val cardId: String,
    val collectionName: String,
    val copies: Int
)