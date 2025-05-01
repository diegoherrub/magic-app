package pol.rubiano.magicapp.features.decks.domain.usecases

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.decks.domain.models.CardInDeck
import pol.rubiano.magicapp.features.decks.domain.models.Deck
import pol.rubiano.magicapp.features.decks.domain.repositories.DecksRepository

@Single
class GetCardsInDeckUseCase(
    private val decksRepository: DecksRepository
) {
    suspend operator fun invoke(deckId: String): List<CardInDeck> {
        return decksRepository.getCardsInDeck(deckId)
    }
}