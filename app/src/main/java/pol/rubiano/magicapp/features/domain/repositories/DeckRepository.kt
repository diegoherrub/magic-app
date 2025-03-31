package pol.rubiano.magicapp.features.domain.repositories

import pol.rubiano.magicapp.features.domain.entities.Deck

interface DeckRepository {
    suspend fun getDecks(): List<Deck>
    suspend fun insertDeck(deck: Deck)
    suspend fun getDeckById(deckId: String): Deck?
    suspend fun addCardToDeck(deckId: String, cardId: String)
    //suspend fun addCardToSideboard(deckId: String, cardId: String)
}
