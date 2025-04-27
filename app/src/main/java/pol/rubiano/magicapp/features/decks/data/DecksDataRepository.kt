package pol.rubiano.magicapp.features.decks.data

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.decks.data.local.DeckLocalDataSource
import pol.rubiano.magicapp.features.decks.domain.models.Deck
import pol.rubiano.magicapp.features.decks.domain.repositories.DecksRepository

@Single
class DecksDataRepository(
    private val local: DeckLocalDataSource
) : DecksRepository {

    override suspend fun createNewDeck(newDeckName: String, newDescriptionName: String): Deck {
        return local.createNewDeck(newDeckName, newDescriptionName)
    }
    override suspend fun getDeckById(deckId: String): Deck? {
        return local.getDeckById(deckId)
    }







    override suspend fun saveDeck(deck: Deck) {

        local.saveDeck(deck)
        // return deck
    }




    override suspend fun getUserDecks(): List<Deck> {
        return local.getUserDecks()
    }




    override suspend fun addCardToDeck(deckId: String, cardId: String) {
    //    local.addCardToDeck(deckId, cardId)
        TODO()
    }
}
