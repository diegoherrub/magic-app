package pol.rubiano.magicapp.features.decks.domain.usecases

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.decks.domain.models.Deck
import pol.rubiano.magicapp.features.decks.domain.repositories.DecksRepository

@Single
class CreateNewDeckUseCase(
    private val decksRepository: DecksRepository
) {
    suspend operator fun invoke(newDeckName: String, newDeckDescription: String): Deck {
        return decksRepository.createNewDeck(newDeckName, newDeckDescription)
    }
}
