package pol.rubiano.magicapp.features.collections.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import pol.rubiano.magicapp.features.collections.domain.Collection

@Dao
interface CollectionDao {
    @Query("SELECT * FROM collections ORDER BY `order` ASC")
    suspend fun getCollections(): List<CollectionEntity>

//    @Update
//    suspend fun updateCollection(collectionEntity: CollectionEntity)

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