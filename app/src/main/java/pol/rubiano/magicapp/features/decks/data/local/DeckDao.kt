package pol.rubiano.magicapp.features.decks.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DeckDao {

    @Query("SELECT COUNT(*) FROM decks")
    suspend fun getDecksCount(): Int

    @Query("SELECT * FROM decks")
    suspend fun getDecks(): List<DeckEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeck(deckEntity: DeckEntity)

    @Query("SELECT * FROM decks WHERE name = :deckName LIMIT 1")
    suspend fun getDeckByName(deckName: String): DeckEntity?

    @Query("SELECT * FROM decks WHERE id = :deckId LIMIT 1")
    suspend fun getDeckById(deckId: String): DeckEntity?

    @Query("SELECT * FROM cards_in_deck WHERE card_id = :cardId AND deck_id = :deckId LIMIT 1")
    suspend fun getCardInDeck(cardId: String, deckId: String): CardInDeckEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCardInDeck(cardInDeckEntity: CardInDeckEntity)

    @Query("SELECT * FROM cards_in_deck WHERE deck_id = :deckId")
    suspend fun getCardsOfDeck(deckId: String): List<CardInDeckEntity>

    @Query("UPDATE decks SET cards = :cardList WHERE id = :deckId")
    suspend fun updateDeck(cardList: List<CardInDeckEntity>, deckId: String)
}
