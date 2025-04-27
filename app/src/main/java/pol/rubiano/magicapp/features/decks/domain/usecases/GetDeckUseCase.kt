package pol.rubiano.magicapp.features.decks.domain.usecases

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.decks.domain.models.Deck
import pol.rubiano.magicapp.features.decks.domain.repositories.DecksRepository

@Single
class GetDeckUseCase(
    private val decksRepository: DecksRepository
) {
    suspend operator fun invoke(deckId: String): Deck? {
        return decksRepository.getDeckById(deckId)
    }
}

