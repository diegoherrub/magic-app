package pol.rubiano.magicapp.features.decks.domain.usecases

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.decks.domain.models.Deck
import pol.rubiano.magicapp.features.decks.domain.repositories.DecksRepository

@Single
class SaveDeckUseCase(
    private val decksRepository: DecksRepository
) {
    suspend operator fun invoke(deck: Deck): Deck {
        return decksRepository.saveDeck(deck)
    }
}
