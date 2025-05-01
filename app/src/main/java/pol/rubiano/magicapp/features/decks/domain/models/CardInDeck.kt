package pol.rubiano.magicapp.features.decks.domain.models

data class CardInDeck(
    val cardId: String,
    val deckId: String,
    var mainBoardCopies: Int,
    var sideBoardCopies: Int,
    var mayBeBoardCopies: Int,
)