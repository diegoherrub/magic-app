package pol.rubiano.magicapp.features.decks.data.local

import android.content.Context
import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.decks.domain.models.Deck

@Single
class DeckLocalDataSource(
    private val deckDao: DeckDao,
    private val context: Context
) {
    suspend fun saveDeck(deckEntity: DeckEntity) {
        return deckDao.insertDeck(deckEntity)
    }

    suspend fun getDecksCount(): Int {
        return deckDao.getDecksCount()
    }

    suspend fun getDeckByName(deckName: String): Deck? {
        val deckEntity = deckDao.getDeckByName(deckName)
        return deckEntity?.toDeck()
    }

    suspend fun getDecks(): List<DeckEntity> {
        return deckDao.getDecks()
    }

    suspend fun getDeckById(id: String): DeckEntity? {
        return deckDao.getDeckById(id)
    }




    //suspend fun addCardToDeck(deckId: String, cardId: String) {
    //    val deck = getDeckById(deckId)
    //    val updatedMainboard = deck.cardIds + cardId
    //    val updatedDeck = deck?.copy(cardIds = updatedMainboard)
    //    saveDeck(updatedDeck)
    //}
}
