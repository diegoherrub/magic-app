package pol.rubiano.magicapp.features.decks.domain.usecases

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.decks.domain.models.Deck
import pol.rubiano.magicapp.features.decks.domain.repositories.DecksRepository

@Single
class GetDecksUseCase(
    private val decksRepository: DecksRepository
) {
    suspend operator fun invoke(): List<Deck> {
        return decksRepository.getDecks()
    }
}