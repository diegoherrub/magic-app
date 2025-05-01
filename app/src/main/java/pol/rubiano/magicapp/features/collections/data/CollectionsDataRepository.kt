package pol.rubiano.magicapp.features.collections.data

import android.content.Context
import org.koin.core.annotation.Single
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.features.collections.data.local.CollectionEntity
import pol.rubiano.magicapp.features.collections.data.local.CollectionsLocalDataSource
import pol.rubiano.magicapp.features.collections.domain.repositories.CollectionsRepository
import pol.rubiano.magicapp.features.collections.domain.models.CardInCollection
import pol.rubiano.magicapp.features.collections.domain.models.Collection

@Single
class CollectionsDataRepository(
    private val local: CollectionsLocalDataSource,
    private val context: Context
) : CollectionsRepository {

    override suspend fun saveCollection(collection: Collection) {
        val defaultName = context.getString(R.string.str_newCollection)
        var collectionNameToSave = collection.name
        val collectionsCount = local.getCollectionsCount()
        val collectionOrder = collectionsCount + 1

        if (collection.name == defaultName) {
            collectionNameToSave = "$defaultName - $collectionOrder"
        } else {
            val existingCollection = local.getCollectionByName(collectionNameToSave)
            if (existingCollection != null) {
                collectionNameToSave += " - $collectionOrder"
            }
        }
        val collectionEntity = CollectionEntity(
            name = collectionNameToSave,
            order = collectionOrder,
            cards = collection.cards
        )
        local.saveCollection(collectionEntity)
    }

    override suspend fun getCollections(): List<Collection> {
        return local.getCollections()
    }

    override suspend fun getCollectionByName(collectionName: String): Collection {
        return local.getCollectionByName(collectionName)!!
    }

    override suspend fun getCardsOfCollection(collectionName: String): List<CardInCollection> {
        return local.getCardsOfCollection(collectionName)
    }

    override suspend fun saveCardInCollection(cardId: String, collectionName: String): Collection {
        return local.saveCardInCollection(cardId, collectionName)!!
    }
}