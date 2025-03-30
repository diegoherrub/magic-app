package pol.rubiano.magicapp.features.domain.entities

data class Deck(
    val id: String,
    val name: String,
    val description: String,
    val cardIds: List<String>,
    val sideBoard: List<String> = emptyList(),
    val maybeBoard: List<String> = emptyList()
)
