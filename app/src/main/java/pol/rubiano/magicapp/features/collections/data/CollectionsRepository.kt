package pol.rubiano.magicapp.features.collections.data

import pol.rubiano.magicapp.features.collections.domain.Collection

interface CollectionsRepository {
    suspend fun getCollections(): List<Collection>
    suspend fun insertCollection(collection: Collection)
}