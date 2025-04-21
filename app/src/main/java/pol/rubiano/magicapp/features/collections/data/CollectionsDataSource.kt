package pol.rubiano.magicapp.features.collections.data

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat.getString
import org.koin.core.annotation.Single
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.features.collections.domain.CardInCollection
import pol.rubiano.magicapp.features.collections.domain.Collection

@Single
class CollectionsDataSource(
    private val collectionDao: CollectionDao,
    private val context: Context
) : CollectionsRepository {

    override suspend fun getCollections(): List<Collection> {
        Log.d("@pol", "start -> CollectionsDataSource.getCollectionsUseCase()")
        val collectionsEntity = collectionDao.getCollectionsDao()
        Log.d("@pol", "end -> CollectionsDataSource.getCollectionsUseCase()")
        Log.d("@pol", "collectionsEntity -> $collectionsEntity")

        return collectionsEntity.map { it.toCollection() }
    }

    override suspend fun getCollectionByName(collectionName: String): Collection {
        val collectionEntity = collectionDao.getCollectionByName(collectionName)
        return collectionEntity.toCollection()
    }

    override suspend fun getCardsOfCollection(collectionName: String): List<CardInCollection> {
        val cardsOfCollectionEntity = collectionDao.getCardsOfCollections(collectionName)
        return cardsOfCollectionEntity.map { it.toCardInCollection() }

    }

    override suspend fun saveCollection(collection: Collection) {

        val defaultName = context.getString(R.string.str_new_collection_title)
        var collectionNameToSave = collection.name
        val collectionsCount = collectionDao.getCollectionsCount()

        if (collection.name == defaultName) {
            collectionNameToSave = "$defaultName - ${collectionsCount + 1}"
        } else {
            val existingCollection = collectionDao.getCollectionByName(collectionNameToSave)
            if (existingCollection != null) {
                collectionNameToSave += " - ${collectionsCount + 1}"
            }
        }
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