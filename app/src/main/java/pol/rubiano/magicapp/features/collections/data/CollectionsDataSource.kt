package pol.rubiano.magicapp.features.collections.data

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat.getString
import org.koin.core.annotation.Single
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.features.collections.domain.Collection

@Single
class CollectionsDataSource(
    private val collections: CollectionDao,
    private val context: Context
) : CollectionsRepository {
    override suspend fun getCollections(): List<Collection> {
        Log.d("@pol", "start -> CollectionsDataSource.getCollections()")
        val collectionsEntity = collections.getCollections()
        Log.d("@pol", "end -> CollectionsDataSource.getCollections()")

        Log.d("@pol","start/end CollectionsDataSource.return(mapa objetos Collection)")
        return collectionsEntity.map { it.toCollection() }
    }

    override suspend fun insertCollection(collection: Collection) {
        val defaultName = context.getString(R.string.str_new_collection_title)
        val collectionsCount = collections.getCollectionsCount()

        if (collection.name == defaultName) {
            collection.name += " - ${collectionsCount + 1}"
        }

        Log.d("@pol", "start -> CollectionsDataSource.collection.toEntity()")
        val collectionEntity = collection.toEntity()
        Log.d("@pol", "end -> CollectionsDataSource.collection.toEntity()")

        Log.d("@pol", "start -> CollectionsDataSource.insertCollectionAtTop(collection)")
        collections.insertCollectionAtTop(collectionEntity)
        Log.d("@pol", "end -> CollectionsDataSource.insertCollectionAtTop(collection)")
    }
}