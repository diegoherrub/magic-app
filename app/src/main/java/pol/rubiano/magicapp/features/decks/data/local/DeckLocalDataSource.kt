package pol.rubiano.magicapp.features.decks.data.local

import android.content.Context
import org.koin.core.annotation.Single
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.features.decks.domain.models.Deck
import java.util.UUID

@Single
class DeckLocalDataSource(
    private val deckDao: DeckDao,
    private val context: Context
) {
    suspend fun createNewDeck(newDeckName: String, newDeckDescription: String): Deck {
        val defaultName = context.getString(R.string.str_newDeck)
        var newDeckNameToSave = newDeckName
        val decksCount = deckDao.getDecksCount()

        if (newDeckNameToSave == defaultName) {
            newDeckNameToSave = "$defaultName - ${decksCount + 1}"
        } else {
            val existingDeck = deckDao.getDeckByName(newDeckNameToSave)
            if (existingDeck != null) {
                newDeckNameToSave += " - ${decksCount + 1}"
            }
        }

        val newDeck = Deck(
            id = UUID.randomUUID().toString(),
            name = newDeckNameToSave,
            description = newDeckDescription,
            colors = emptyList(),
            cardIds = emptyList(),
            sideBoard = emptyList(),
            maybeBoard = emptyList()
        )

        return saveDeck(newDeck)
    }

    suspend fun saveDeck(deck: Deck): Deck {
        deckDao.insertDeck(deck.toEntity())
        return deck
    }
    suspend fun getDeckById(id: String): Deck? {
        return deckDao.getDeckById(id)?.toDeck()
    }














    suspend fun getUserDecks(): List<Deck> {
        val deckEntities = deckDao.getUserDecks()
        return deckEntities.map { it.toDeck() }
    }



    //suspend fun addCardToDeck(deckId: String, cardId: String) {
    //    val deck = getDeckById(deckId)
    //    val updatedMainboard = deck.cardIds + cardId
    //    val updatedDeck = deck?.copy(cardIds = updatedMainboard)
    //    saveDeck(updatedDeck)
    //}
}
