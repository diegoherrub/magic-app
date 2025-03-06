package pol.rubiano.magicapp.app.data.local.datasources

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.app.data.local.daos.CardDao
import pol.rubiano.magicapp.app.data.local.dbmappers.toEntity
import pol.rubiano.magicapp.app.domain.entities.Card

@Single
class CardLocalDataSource(
    private val dao: CardDao
) {
    suspend fun saveCardToLocal(card: Card) {
        val cardEntity = card.toEntity()
        dao.saveCardToLocal(cardEntity)
    }

    suspend fun saveCardsToLocal(cards: List<Card>) {
        val cardsEntities = cards.map { it.toEntity() }
        dao.saveCardsToLocal(*cardsEntities.toTypedArray())
    }
}