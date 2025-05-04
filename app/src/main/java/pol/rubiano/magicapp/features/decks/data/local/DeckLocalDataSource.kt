package pol.rubiano.magicapp.features.decks.data.local

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.decks.domain.models.Deck
import pol.rubiano.magicapp.features.decks.domain.models.CardInDeck

@Single
class DeckLocalDataSource(
    private val deckDao: DeckDao,
) {
    suspend fun saveDeck(deckEntity: DeckEntity) {
        return deckDao.insertDeck(deckEntity)
    }

    suspend fun getDecksCount(): Int {
        return deckDao.getDecksCount()
    }

    suspend fun getDecks(): List<DeckEntity> {
        return deckDao.getDecks()
    }

    suspend fun getDeckByName(deckName: String): Deck? {
        val deckEntity = deckDao.getDeckByName(deckName)
        return deckEntity?.toDeck()
    }

    suspend fun getDeckById(id: String): Deck? {
        val deckEntity = deckDao.getDeckById(id)
        return deckEntity?.toDeck()
    }

    suspend fun getCardsInDeck(deckId: String): List<CardInDeck> {
        return deckDao.getCardsOfDeck(deckId).map { it.toCardInDeck() }
    }

    suspend fun saveCardInDeck(cardId: String, deckId: String): Deck? {
        val existingCardInDeck = deckDao.getCardInDeck(cardId, deckId)
        if (existingCardInDeck == null) {
            val cardInDeck = CardInDeckEntity(
                cardId = cardId,
                deckId = deckId,
                mainBoardCopies = 1,
                sideBoardCopies = 0,
                mayBeBoardCopies = 0
            )
            deckDao.saveCardInDeck(cardInDeck)
        }
        return refreshDeck(deckId)
    }

    private suspend fun refreshDeck(deckId: String): Deck? {
        val cardsInDeck = getCardsInDeck(deckId)
        val cardsInDeckEntities = cardsInDeck.map { it.toEntity() }
        deckDao.updateDeck(cardsInDeckEntities, deckId)
        return getDeckById(deckId)
    }
}
