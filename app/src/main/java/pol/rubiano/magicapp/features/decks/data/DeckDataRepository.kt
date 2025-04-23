package pol.rubiano.magicapp.features.decks.data

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.decks.data.local.DeckDataSource
import pol.rubiano.magicapp.features.decks.domain.models.Deck
import pol.rubiano.magicapp.features.decks.domain.repositories.DeckRepository

@Single
class DeckDataRepository(
    private val local: DeckDataSource
) : DeckRepository {

    override suspend fun getUserDecks(): List<Deck> {
        return local.getUserDecks()
    }

    override suspend fun insertDeck(deck: Deck): Deck {
        local.insertDeck(deck)
        return deck
    }

    override suspend fun getDeckById(deckId: String): Deck {
        return local.getDeckById(deckId)
    }

    override suspend fun addCardToDeck(deckId: String, cardId: String) {
        local.addCardToDeck(deckId, cardId)
    }
}
