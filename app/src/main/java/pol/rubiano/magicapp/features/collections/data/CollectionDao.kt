package pol.rubiano.magicapp.features.collections.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface CollectionDao {
    @Query("SELECT * FROM collections ORDER BY `order` ASC")
    suspend fun getCollections(): List<CollectionEntity>

    @Query("SELECT * FROM collections WHERE `name` = :collectionName")
    suspend fun getCollection(collectionName: String) : CollectionEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollection(collection: CollectionEntity)

    @Query("UPDATE collections SET `order` = `order` + 1")
    suspend fun incrementOrderOfAllCollections()

    @Transaction
    suspend fun insertCollectionAtTop(collectionEntity: CollectionEntity) {
        incrementOrderOfAllCollections()
        insertCollection(collectionEntity)
    }

    @Query("SELECT COUNT(*) FROM collections")
    suspend fun getCollectionsCount(): Int
}