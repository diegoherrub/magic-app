package pol.rubiano.magicapp.app.cards.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCard(card: CardEntity)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun saveCardsToLocal(vararg card: CardEntity)

    @Query("SELECT * FROM cards WHERE id = :id LIMIT 1")
    suspend fun getCardById(id: String): CardEntity?

//    @Delete
//    suspend fun deleteLocalCard(card: CardEntity)
}