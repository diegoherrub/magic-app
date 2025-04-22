package pol.rubiano.magicapp.features.cards.domain.repositories

import pol.rubiano.magicapp.features.cards.domain.models.Card

interface CardRepository {
    suspend fun saveCardToLocal(card: Card)
    suspend fun getRandomCard(): Result<Card>
    suspend fun getLocalCardById(cardId: String): Card?
}