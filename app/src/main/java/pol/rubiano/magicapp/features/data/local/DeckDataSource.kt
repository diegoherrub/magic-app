package pol.rubiano.magicapp.features.data.local

import android.util.Log
import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.domain.models.Deck

@Single
class DeckDataSource(
    private val deckDao: DeckDao
) {
    suspend fun getDecks(): List<Deck> {
        val deckEntities = deckDao.getAllDecks()
        Log.d("@DeckDataSource", "Loaded decks count: ${deckEntities.size}")
        return deckEntities.map { it.toDeck() }
    }

    suspend fun insertDeck(deck: Deck) {
        deckDao.insertDeck(deck.toEntity())

    }

    suspend fun getDeckById(id: String): Deck? {
        return deckDao.getDeckById(id)?.toDeck()
    }

    suspend fun addCardToDeck(deckId: String, cardId: String) {
        val deck = getDeckById(deckId)
        if (deck != null) {
            val updatedMainboard = deck.cardIds + cardId
            val updatedDeck = deck.copy(cardIds = updatedMainboard)
            insertDeck(updatedDeck)
        }
    }
}
