package pol.rubiano.magicapp.features.cards.data.local

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.cards.domain.models.Card
import pol.rubiano.magicapp.features.collections.data.local.CardInCollectionEntity
import pol.rubiano.magicapp.features.collections.data.local.toCardInCollection
import pol.rubiano.magicapp.features.collections.data.local.toEntity
import pol.rubiano.magicapp.features.collections.domain.CardInCollection

@Single
class CardLocalDataSource(
    private val cardDao: CardDao
) {
    suspend fun saveCardToLocal(card: Card) {
        val cardEntity = card.toEntity()
        cardDao.saveCardToLocal(cardEntity)
    }

    suspend fun getLocalCardById(cardId: String): Card? {
        return cardDao.getCardById(cardId)?.toDomain()
    }

    suspend fun getCardInCollection(cardId: String, collectionName: String): CardInCollection {
        return cardDao.getCardInCollection(cardId, collectionName).toCardInCollection()
    }

    suspend fun saveCardInCollectionToLocal(cardInCollection: CardInCollection) {
        cardDao.saveCardInCollection(cardInCollection.toEntity())
    }

    suspend fun updateLocalCardInCollection(cardInCollection: CardInCollection) {
        cardDao.updateLocalCardInCollection(cardInCollection.toEntity())
    }
}