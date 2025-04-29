package pol.rubiano.magicapp.features.collections.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import pol.rubiano.magicapp.features.collections.domain.CardInCollection
import pol.rubiano.magicapp.features.collections.domain.Collection

@Dao
interface CollectionDao {

    @Query("SELECT * FROM collections ORDER BY `order` ASC")
    suspend fun getCollectionsDao(): List<CollectionEntity>

    @Query("SELECT COUNT(*) FROM collections")
    suspend fun getCollectionsCount(): Int

    @Query("SELECT * FROM collections WHERE `name` = :collectionName LIMIT 1")
    suspend fun getCollectionByName(collectionName: String): CollectionEntity

    @Transaction
    suspend fun saveCollection(collectionEntity: CollectionEntity) {
        incrementOrderOfAllCollections()
        insertCollection(collectionEntity)
    }

    @Query("UPDATE collections SET `order` = `order` + 1")
    suspend fun incrementOrderOfAllCollections()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollection(collection: CollectionEntity)



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCardInCollection(cardInCollectionEntity: CardInCollectionEntity)

    @Update
    suspend fun updateCardInCollection(cardInCollectionEntity: CardInCollectionEntity)

    @Query("UPDATE collections SET cards = :cardList WHERE name = :collectionName")
    suspend fun updateCollection(cardList: List<CardInCollectionEntity>, collectionName: String)


   @Query("SELECT * FROM cards_in_collection WHERE card_id = :cardId and collection_name = :collectionName")
   suspend fun getCardInCollection(cardId: String, collectionName: String): CardInCollectionEntity

   @Query("SELECT * FROM cards_in_collection WHERE collection_name = :collectionName")
   suspend fun getCardsOfCollection(collectionName: String): List<CardInCollectionEntity>
}