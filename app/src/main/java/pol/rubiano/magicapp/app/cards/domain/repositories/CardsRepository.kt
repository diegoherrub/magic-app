package pol.rubiano.magicapp.app.cards.domain.repositories

import pol.rubiano.magicapp.app.domain.models.Card

interface CardsRepository {
        suspend fun getRandomCard(): Result<Card>
    //    suspend fun saveCard(card: Card): Card?
    suspend fun saveCard(card: Card)
    suspend fun getCardById(cardId: String): Card?

}