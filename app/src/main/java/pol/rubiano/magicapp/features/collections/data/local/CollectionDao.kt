package pol.rubiano.magicapp.features.collections.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import pol.rubiano.magicapp.features.collections.domain.CardInCollection

@Dao
interface CollectionDao {

    @Query("SELECT * FROM collections ORDER BY `order` ASC")
    suspend fun getCollectionsDao(): List<CollectionEntity>

    @Query("SELECT COUNT(*) FROM collections")
    suspend fun getCollectionsCount(): Int

    @Query("SELECT * FROM collections WHERE `name` = :collectionName LIMIT 1")
    suspend fun getCollectionByName(collectionName: String) : CollectionEntity

    @Transaction
    suspend fun saveCollection(collectionEntity: CollectionEntity) {
        incrementOrderOfAllCollections()
        insertCollection(collectionEntity)
    }

    @Query("UPDATE collections SET `order` = `order` + 1")
    suspend fun incrementOrderOfAllCollections()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollection(collection: CollectionEntity)





   @Query("UPDATE cards_in_collection SET copies = :copies WHERE card_id = :cardId AND collection_name = :collectionName")
   suspend fun updateCardInCollection(cardId: String, collectionName: String, copies: Int)

   // @Insert(onConflict = OnConflictStrategy.REPLACE)
   // suspend fun insertCardInCollection(card: CardInCollectionEntity)

   // @Query("SELECT * FROM cards_in_collection WHERE collection_name = :collectionName")
   // suspend fun getCardsOfCollection(collectionName: String): List<CardInCollectionEntity>
}