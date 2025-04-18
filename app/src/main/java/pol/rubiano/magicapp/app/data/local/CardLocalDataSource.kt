package pol.rubiano.magicapp.app.data.local

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.app.domain.models.Card

@Single
class CardLocalDataSource(
    private val dao: CardDao
) {
    suspend fun saveCardToLocal(card: Card): Card {
        val cardEntity = card.toEntity()
        dao.saveCardToLocal(cardEntity)
        return card
    }

    suspend fun getCardById(cardId: String): Card? {
        return dao.getCardById(cardId)?.toDomain()
    }
}