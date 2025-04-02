package pol.rubiano.magicapp.features.domain.repositories

import pol.rubiano.magicapp.features.domain.models.Deck

interface DeckRepository {
    suspend fun getUserDecks(): List<Deck>
    suspend fun insertDeck(deck: Deck): Deck?
    suspend fun getDeckById(deckId: String): Deck?
    suspend fun addCardToDeck(deckId: String, cardId: String)
    //suspend fun addCardToSideboard(deckId: String, cardId: String)
}
