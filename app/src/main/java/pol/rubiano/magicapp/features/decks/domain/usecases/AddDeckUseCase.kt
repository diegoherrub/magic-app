package pol.rubiano.magicapp.features.decks.domain.usecases

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.decks.domain.models.Deck
import pol.rubiano.magicapp.features.decks.domain.repositories.DeckRepository

@Single
class AddDeckUseCase(
    private val deckRepository: DeckRepository
) {
    suspend operator fun invoke(deck: Deck) {
        deckRepository.insertDeck(deck)
    }
}
