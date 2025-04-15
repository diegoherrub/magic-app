package pol.rubiano.magicapp.features.collections.data

import android.content.Context
import org.koin.core.annotation.Single
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.features.collections.domain.Collection

@Single
class CollectionsDataSource(
    private val collections: CollectionDao,
    private val context: Context
) : CollectionsRepository {

    override suspend fun getCollections(): List<Collection> {
        val collectionsEntity = collections.getCollections()
        return collectionsEntity.map { it.toCollection() }
    }

    override suspend fun getCollection(collectionName: String): Collection {
        val collectionEntity = collections.getCollection(collectionName)
        return collectionEntity.toCollection()
    }

    override suspend fun insertCollection(collection: Collection) {
        val defaultName = context.getString(R.string.str_new_collection_title)
        val collectionsCount = collections.getCollectionsCount()

        if (collection.name == defaultName) {
            collection.name += " - ${collectionsCount + 1}"
        } else {
            val existingCollection = collections.getCollection(collection.name)
            if (collection.name == existingCollection.name) {
                collection.name += " - ${collectionsCount + 1}"
            }
        }

        val collectionEntity = collection.toEntity()
        collections.insertCollectionAtTop(collectionEntity)
    }
}