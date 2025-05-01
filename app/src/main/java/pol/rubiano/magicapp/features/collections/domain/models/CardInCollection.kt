package pol.rubiano.magicapp.features.collections.domain.models

data class CardInCollection(
    val cardId: String,
    val collectionName: String,
    var copies: Int
)