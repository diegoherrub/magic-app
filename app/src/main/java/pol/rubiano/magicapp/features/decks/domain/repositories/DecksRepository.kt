package pol.rubiano.magicapp.features.decks.domain.repositories

import pol.rubiano.magicapp.features.decks.domain.models.CardInDeck
import pol.rubiano.magicapp.features.decks.domain.models.Deck

interface DecksRepository {
    suspend fun saveDeck(deck: Deck): Deck
    suspend fun getDecks(): List<Deck>
    suspend fun getDeckById(deckId: String): Deck?
    suspend fun saveCardInDeck(cardId: String, deckId: String): Deck
    suspend fun getCardsInDeck(deckId: String): List<CardInDeck>
}
