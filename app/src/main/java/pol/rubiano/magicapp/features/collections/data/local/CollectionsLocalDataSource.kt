package pol.rubiano.magicapp.features.collections.data.local

import android.util.Log
import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.cards.data.local.CardDao
import pol.rubiano.magicapp.features.cards.data.local.toEntity
import pol.rubiano.magicapp.features.cards.domain.models.Card
import pol.rubiano.magicapp.features.collections.domain.CardInCollection
import pol.rubiano.magicapp.features.collections.domain.Collection

@Single
class CollectionsLocalDataSource(
    private val collectionDao: CollectionDao,
    private val cardDao: CardDao
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

    suspend fun addCardToCollection(card: Card, collectionName: String) {
        val existingCardInCollection = collectionDao.getCardInCollection(card.id, collectionName)
        if (existingCardInCollection == null) {
            val cardInCollection = CardInCollectionEntity(
                cardId = card.id,
                collectionName = collectionName,
                copies = 0
            )
            collectionDao.insertCardInCollection(cardInCollection)
            cardDao.saveCardToLocal(card.toEntity())
        } else {
            existingCardInCollection.copies += 1
            val cardId = existingCardInCollection.cardId
            val collectionName = existingCardInCollection.collectionName
            val copies = existingCardInCollection.copies
            collectionDao.updateCardInCollection(cardId, collectionName, copies)
        }
    }
}

