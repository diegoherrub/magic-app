package pol.rubiano.magicapp.features.cards.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCardToLocal(card: CardEntity)

    @Query("SELECT * FROM cards WHERE id = :id LIMIT 1")
    suspend fun getCardById(id: String): CardEntity?
}