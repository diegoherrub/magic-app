package pol.rubiano.magicapp.features.data

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.data.local.DeckDataSource
import pol.rubiano.magicapp.features.domain.models.Deck
import pol.rubiano.magicapp.features.domain.repositories.DeckRepository

@Single
class DeckDataRepository(
    private val local: DeckDataSource
) : DeckRepository {

    override suspend fun getDecks(): List<Deck> {
        return local.getDecks()
    }

    override suspend fun insertDeck(deck: Deck) {
        local.insertDeck(deck)
    }

    override suspend fun getDeckById(deckId: String): Deck? {
        return local.getDeckById(deckId)
    }

    override suspend fun addCardToDeck(deckId: String, cardId: String) {
        local.addCardToDeck(deckId, cardId)
    }
}
