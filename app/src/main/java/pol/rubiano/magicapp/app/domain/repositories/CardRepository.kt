package pol.rubiano.magicapp.app.domain.repositories

import pol.rubiano.magicapp.app.data.local.CardEntity
import pol.rubiano.magicapp.app.domain.models.Card

interface CardRepository {
    suspend fun getRandomCard(): Result<Card>
    suspend fun getCardById(cardId: String): Card?
    suspend fun saveCardToLocal(card: Card)

}