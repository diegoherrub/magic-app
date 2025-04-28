package pol.rubiano.magicapp.features.cards.domain.repositories

import pol.rubiano.magicapp.features.cards.domain.models.Card
import pol.rubiano.magicapp.features.collections.domain.CardInCollection

interface CardRepository {
    suspend fun saveCardToLocal(card: Card)
    suspend fun getLocalCardById(cardId: String): Card?
    suspend fun createCardInCollection(card: Card, collectionName: String): CardInCollection
    //suspend fun getCardInCollection(cardInCollection: CardInCollection): CardInCollection
    suspend fun addCardToCollection(card: Card, collectionName: String)

    //suspend fun insertCardInCollection(cardInCollection: CardInCollection, collectionName: String)

    //suspend fun updateCardsInCollection(card: Card, collectionName: String)


    suspend fun getRandomCard(): Result<Card>
}