package pol.rubiano.magicapp.features.search.domain.usecases

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.cards.domain.models.Card
import pol.rubiano.magicapp.features.search.domain.repositories.ScryfallRepository

@Single
class SaveCardToDeckUseCase(
    private val scryfallRepository: ScryfallRepository
) {
    suspend operator fun invoke(card: Card, deckName: String) {
        TODO()
    }
}