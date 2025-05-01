package pol.rubiano.magicapp.features.collections.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CollectionDao {

    @Query("SELECT COUNT(*) FROM collections")
    suspend fun getCollectionsCount(): Int

    @Query("SELECT * FROM collections ORDER BY `order` DESC")
    suspend fun getCollections(): List<CollectionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollection(collection: CollectionEntity)

    @Query("SELECT * FROM collections WHERE `name` = :collectionName LIMIT 1")
    suspend fun getCollectionByName(collectionName: String): CollectionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCardInCollection(cardInCollectionEntity: CardInCollectionEntity)

    @Query("SELECT * FROM cards_in_collection WHERE collection_name = :collectionName")
    suspend fun getCardsOfCollection(collectionName: String): List<CardInCollectionEntity>

    @Query("UPDATE collections SET cards = :cardList WHERE name = :collectionName")
    suspend fun updateCollection(cardList: List<CardInCollectionEntity>, collectionName: String)

    @Query("SELECT * FROM cards_in_collection WHERE card_id = :cardId and collection_name = :collectionName")
    suspend fun getCardInCollection(cardId: String, collectionName: String): CardInCollectionEntity

    @Update
    suspend fun updateCardInCollection(cardInCollectionEntity: CardInCollectionEntity)
}