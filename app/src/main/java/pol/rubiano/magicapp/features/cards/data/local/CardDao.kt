package pol.rubiano.magicapp.features.cards.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import pol.rubiano.magicapp.features.collections.data.local.CardInCollectionEntity
import pol.rubiano.magicapp.features.collections.domain.CardInCollection

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCardToLocal(card: CardEntity)

    @Query("SELECT * FROM cards WHERE id = :id LIMIT 1")
    suspend fun getCardById(id: String): CardEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCardInCollectionToLocal(cardInCollectionEntity: CardInCollectionEntity)

    @Query("SELECT * FROM cards_in_collection WHERE card_id = :cardId and collection_name = :collectionName")
    suspend fun getCardInCollection(cardId: String, collectionName: String): CardInCollectionEntity

    @Update
    suspend fun updateLocalCardInCollection(cardInCollectionEntity: CardInCollectionEntity)


//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun saveCardsToLocal(vararg card: CardEntity)


//    @Delete
//    suspend fun deleteLocalCard(card: CardEntity)
}