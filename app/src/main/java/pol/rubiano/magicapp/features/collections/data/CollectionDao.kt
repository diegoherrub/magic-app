package pol.rubiano.magicapp.features.collections.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pol.rubiano.magicapp.features.collections.domain.Collection

@Dao
interface CollectionDao {
    @Query("SELECT * FROM collections")
    suspend fun getCollections(): List<CollectionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollection(collection: CollectionEntity)
}