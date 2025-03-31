package pol.rubiano.magicapp.features.domain.usecases

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.domain.models.Deck
import pol.rubiano.magicapp.features.domain.repositories.DeckRepository

@Single
class AddDeckUseCase(
    private val deckRepository: DeckRepository
) {
    suspend operator fun invoke(deck: Deck) {
        deckRepository.insertDeck(deck)
    }
}
