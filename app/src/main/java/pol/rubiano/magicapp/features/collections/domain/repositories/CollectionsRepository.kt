package pol.rubiano.magicapp.features.collections.domain.repositories

import pol.rubiano.magicapp.features.cards.domain.models.Card
import pol.rubiano.magicapp.features.collections.domain.CardInCollection
import pol.rubiano.magicapp.features.collections.domain.Collection

interface CollectionsRepository {
    suspend fun saveCollection(collection: Collection)
    suspend fun getCollections(): List<Collection>
    suspend fun getCollectionByName(collectionName: String): Collection
    suspend fun addCardToCollection(card: Card, collectionName: String)
    suspend fun getCardsOfCollection(collectionName: String): List<CardInCollection>


    suspend fun createCardInCollection(card: Card, collectionName: String): CardInCollection
}