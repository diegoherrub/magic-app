package pol.rubiano.magicapp.features.cards.domain.repositories

import pol.rubiano.magicapp.features.cards.domain.models.Card

interface CardRepository {
    suspend fun saveCard(card: Card)
    suspend fun getCard(cardId: String): Card?
    suspend fun getRandomCard(): Result<Card>
}