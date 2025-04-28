package pol.rubiano.magicapp.features.collections.data

import android.content.Context
import android.util.Log
import org.koin.core.annotation.Single
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.features.collections.data.local.CollectionDao
import pol.rubiano.magicapp.features.collections.data.local.CollectionEntity
import pol.rubiano.magicapp.features.collections.data.local.CollectionsLocalDataSource
import pol.rubiano.magicapp.features.collections.data.local.toCardInCollection
import pol.rubiano.magicapp.features.collections.data.local.toCollection
import pol.rubiano.magicapp.features.collections.data.local.toEntity
import pol.rubiano.magicapp.features.collections.domain.repositories.CollectionsRepository
import pol.rubiano.magicapp.features.collections.domain.CardInCollection
import pol.rubiano.magicapp.features.collections.domain.Collection

@Single
class CollectionsDataRepository(
    private val local: CollectionsLocalDataSource,
    private val context: Context
) : CollectionsRepository {

    override suspend fun getCollections(): List<Collection> {
        return local.getLocalCollections()
    }

    override suspend fun getCollectionByName(collectionName: String): Collection {
        return local.getCollectionByName(collectionName)
    }

    override suspend fun getCardsOfCollection(collectionName: String): List<CardInCollection> {
        return local.getCardsOfCollection(collectionName)
    }

    override suspend fun saveCollection(collection: Collection) {
        val defaultName = context.getString(R.string.str_newCollection)
        var collectionNameToSave = collection.name
        val collectionsCount = local.getLocalCollectionsCount()

        if (collection.name == defaultName) {
            collectionNameToSave = "$defaultName - ${collectionsCount + 1}"
        } else {
            val existingCollection = collectionDao.getCollectionByName(collectionNameToSave)
            if (existingCollection != null) {
                collectionNameToSave += " - ${collectionsCount + 1}"
            }
        }
        Log.d("@pol", "collectiondatasource $collectionNameToSave")

        val collectionEntity = CollectionEntity(
            name = collectionNameToSave,
            order = collection.order,
            cards = collection.cards
        )
        collectionDao.saveCollection(collectionEntity)
    }

    override suspend fun addCardToCollection(collectionName: String, cardId: String) {
        val collection = collectionDao.getCollectionByName(collectionName)
        val cardInCollection = collection.cards.find { it.cardId == cardId }
        if (cardInCollection != null) {
            cardInCollection.copies += 1
            collectionDao.updateCardInCollection(
                cardId = cardInCollection.cardId,
                collectionName = cardInCollection.collectionName,
                copies = cardInCollection.copies
            )
        } else {
            val newCardInCollection = CardInCollection(
                cardId = cardId,
                collectionName = collectionName,
                copies = 1
            )
            collectionDao.insertCardInCollection(newCardInCollection.toEntity())
        }
    }
}