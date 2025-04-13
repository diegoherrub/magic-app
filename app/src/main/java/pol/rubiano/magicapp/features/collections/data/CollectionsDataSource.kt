package pol.rubiano.magicapp.features.collections.data

import android.util.Log
import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.collections.domain.Collection

@Single
class CollectionsDataSource(
    private val collections: CollectionDao
) : CollectionsRepository {
    override suspend fun getCollections(): List<Collection> {
        Log.d("@pol", "start -> CollectionsDataSource.getCollections()")
        val collectionsEntity = collections.getCollections()
        Log.d("@pol", "end -> CollectionsDataSource.getCollections()")
        Log.d("@pol","start/end CollectionsDataSource.return(mapa objetos Collection)")
        return collectionsEntity.map { it.toCollection() }
    }

    override suspend fun insertCollection(collection: Collection) {
        Log.d("@pol", "start -> CollectionsDataSource.collection.toEntity()")
        val collectionEntity = collection.toEntity()
        Log.d("@pol", "end -> CollectionsDataSource.collection.toEntity()")
        Log.d("@pol", "start -> CollectionsDataSource.insertCollection(collection)")
        collections.insertCollection(collectionEntity)
        Log.d("@pol", "end -> CollectionsDataSource.insertCollection(collection)")
    }
}