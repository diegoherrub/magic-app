package pol.rubiano.magicapp.features.cards.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pol.rubiano.magicapp.app.cards.data.local.CardEntity

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCardToLocal(card: CardEntity)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun saveCardsToLocal(vararg card: CardEntity)

    @Query("SELECT * FROM cards WHERE id = :id LIMIT 1")
    suspend fun getCardById(id: String): CardEntity?

//    @Delete
//    suspend fun deleteLocalCard(card: CardEntity)
}