package pol.rubiano.magicapp.features.decks.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pol.rubiano.magicapp.features.decks.domain.models.Deck

@Dao
interface DeckDao {
    @Query("SELECT COUNT(*) FROM decks")
    suspend fun getDecksCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeck(deck: DeckEntity)

    @Query("SELECT * FROM decks WHERE name = :deckName LIMIT 1")
    suspend fun getDeckByName(deckName: String): DeckEntity

    @Query("SELECT * FROM decks WHERE id = :deckId LIMIT 1")
    suspend fun getDeckById(deckId: String): DeckEntity?







    @Query("SELECT * FROM decks")
    suspend fun getUserDecks(): List<DeckEntity>




}
