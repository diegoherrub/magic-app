package pol.rubiano.magicapp.features.collections.data

import pol.rubiano.magicapp.app.domain.models.Card
import pol.rubiano.magicapp.features.collections.domain.CardInCollection
import pol.rubiano.magicapp.features.collections.domain.Collection

interface CollectionsRepository {
    suspend fun getCollections(): List<Collection>
    suspend fun getCollection(collectionName: String): Collection
    suspend fun insertCollection(collection: Collection)
}