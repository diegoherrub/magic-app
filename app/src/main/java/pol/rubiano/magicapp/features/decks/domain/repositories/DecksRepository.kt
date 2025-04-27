package pol.rubiano.magicapp.features.decks.domain.repositories

import pol.rubiano.magicapp.features.decks.domain.models.Deck

interface DecksRepository {
    suspend fun createNewDeck(newDeckName: String, newDescriptionName: String): Deck
    suspend fun getDeckById(deckId: String): Deck?

    suspend fun saveDeck(deck: Deck)
    suspend fun getUserDecks(): List<Deck>
    suspend fun addCardToDeck(deckId: String, cardId: String)
}
