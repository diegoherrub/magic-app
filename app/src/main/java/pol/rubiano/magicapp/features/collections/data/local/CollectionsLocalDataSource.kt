package pol.rubiano.magicapp.features.collections.data.local

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.collections.domain.CardInCollection
import pol.rubiano.magicapp.features.collections.domain.Collection

@Single
class CollectionsLocalDataSource(
    private val collectionDao: CollectionDao,
) {
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

    //suspend fun getCardsOfCollection(collectionName: String): List<CardInCollection> {
    //    return collectionDao.getCardsOfCollection(collectionName).map { it.toCardInCollection() }
    //}

    suspend fun saveCollection(collectionEntity: CollectionEntity) {
        collectionDao.saveCollection(collectionEntity)
    }
}