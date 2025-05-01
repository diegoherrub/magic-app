package pol.rubiano.magicapp.features.decks.domain.usecases

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.decks.domain.models.Deck
import pol.rubiano.magicapp.features.decks.domain.repositories.DecksRepository

@Single
class SaveCardInDeckUseCase(
    private val decksRepository: DecksRepository
) {
    suspend operator fun invoke(cardId: String, deckId: String): Deck {
        return decksRepository.saveCardInDeck(cardId, deckId)
    }
}