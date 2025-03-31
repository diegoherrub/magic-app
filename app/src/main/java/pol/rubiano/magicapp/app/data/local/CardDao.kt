package pol.rubiano.magicapp.app.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CardDao {

    @Query("SELECT * FROM cards WHERE id = :id LIMIT 1")
    suspend fun getCardById(id: String): CardEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCardToLocal(card: CardEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCardsToLocal(vararg card: CardEntity)

    @Delete
    suspend fun deleteLocalCard(card: CardEntity)
}