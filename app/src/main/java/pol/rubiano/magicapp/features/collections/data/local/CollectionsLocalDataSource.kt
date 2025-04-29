package pol.rubiano.magicapp.features.collections.data.local

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.collections.domain.CardInCollection
import pol.rubiano.magicapp.features.collections.domain.Collection

@Single
class CollectionsLocalDataSource(
    private val collectionDao: CollectionDao
) {
    suspend fun saveCollection(collectionEntity: CollectionEntity) {
        collectionDao.saveCollection(collectionEntity)
    }
    suspend fun getLocalCollectionsCount(): Int {
        return collectionDao.getCollectionsCount()
    }

    suspend fun getLocalCollections(): List<Collection> {
        val collectionsEntity = collectionDao.getCollectionsDao()
        return collectionsEntity.map { it.toCollection() }
    }

    suspend fun getCollectionByName(collectionName: String): Collection {
        val collectionEntity = collectionDao.getCollectionByName(collectionName)
        return collectionEntity.toCollection()
    }

    suspend fun getCardsOfCollection(collectionName: String): List<CardInCollection> {
        return collectionDao.getCardsOfCollection(collectionName).map { it.toCardInCollection() }
    }

    suspend fun saveCardInCollection(cardId: String, collectionName: String): Collection {
        val existingCardInCollection = collectionDao.getCardInCollection(cardId, collectionName)
        if (existingCardInCollection == null) {
            val cardInCollection = CardInCollectionEntity(
                cardId = cardId,
                collectionName = collectionName,
                copies = 1
            )
            collectionDao.saveCardInCollection(cardInCollection)
        } else {
            existingCardInCollection.copies += 1
        }
        return refreshCollection(collectionName)
    }

    private suspend fun refreshCollection(collectionName: String): Collection {
        val cardsInCollection = getCardsOfCollection(collectionName)
        val cardsInCollectionEntities = cardsInCollection.map { it.toEntity() }
        collectionDao.updateCollection(cardsInCollectionEntities, collectionName)
        return getCollectionByName(collectionName)
    }
}

