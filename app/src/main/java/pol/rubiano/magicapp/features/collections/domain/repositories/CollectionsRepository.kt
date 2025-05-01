package pol.rubiano.magicapp.features.collections.domain.repositories

import pol.rubiano.magicapp.features.collections.domain.models.CardInCollection
import pol.rubiano.magicapp.features.collections.domain.models.Collection

interface CollectionsRepository {
    suspend fun saveCollection(collection: Collection)
    suspend fun getCollections(): List<Collection>
    suspend fun getCollectionByName(collectionName: String): Collection
    suspend fun saveCardInCollection(cardId: String, collectionName: String): Collection

    suspend fun getCardsOfCollection(collectionName: String): List<CardInCollection>
}