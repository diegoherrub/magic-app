package pol.rubiano.magicapp.features.decks.domain.models

data class Deck(
    val id: String,
    val name: String,
    val description: String,
    val colors: List<String> = emptyList(),
    val cards: List<CardInDeck> = mutableListOf()
)
