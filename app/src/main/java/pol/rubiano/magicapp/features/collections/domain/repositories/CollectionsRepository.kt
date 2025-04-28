package pol.rubiano.magicapp.features.collections.domain.repositories

import pol.rubiano.magicapp.features.collections.domain.CardInCollection
import pol.rubiano.magicapp.features.collections.domain.Collection

interface CollectionsRepository {
    suspend fun getCollections(): List<Collection>
    suspend fun getCollectionByName(collectionName: String): Collection
    //suspend fun getCardsOfCollection(collectionName: String): List<CardInCollection>
    suspend fun saveCollection(collection: Collection)
    //suspend fun addCardToCollection(collectionName: String, cardId: String)
}