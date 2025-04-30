package pol.rubiano.magicapp.features.decks.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DeckDao {

    @Query("SELECT COUNT(*) FROM decks")
    suspend fun getDecksCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeck(deckEntity: DeckEntity)

    @Query("SELECT * FROM decks WHERE name = :deckName LIMIT 1")
    suspend fun getDeckByName(deckName: String): DeckEntity?

    @Query("SELECT * FROM decks")
    suspend fun getDecks(): List<DeckEntity>

    @Query("SELECT * FROM decks WHERE id = :deckId LIMIT 1")
    suspend fun getDeckById(deckId: String): DeckEntity?
}
