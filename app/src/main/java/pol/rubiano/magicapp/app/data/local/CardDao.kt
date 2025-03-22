package pol.rubiano.magicapp.app.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.RoomWarnings
import pol.rubiano.magicapp.app.domain.Card

@Dao
interface CardDao {
    @RewriteQueriesToDropUnusedColumns
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM cards WHERE id = :id")
    suspend fun getCardById(id: String): Card

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveCardToLocal(Card: CardEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveCardsToLocal(vararg Card: CardEntity)

    @Delete
    suspend fun deleteLocalCard(card: CardEntity)
}