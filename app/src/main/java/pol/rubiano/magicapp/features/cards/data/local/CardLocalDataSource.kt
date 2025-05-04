package pol.rubiano.magicapp.features.cards.data.local

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.cards.domain.models.Card

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
}