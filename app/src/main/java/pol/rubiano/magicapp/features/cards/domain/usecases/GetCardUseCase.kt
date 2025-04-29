package pol.rubiano.magicapp.features.cards.domain.usecases

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.cards.domain.repositories.CardRepository
import pol.rubiano.magicapp.features.cards.domain.models.Card

@Single
class GetCardUseCase(
    private val repository: CardRepository
) {
    suspend operator fun invoke(cardId: String): Card? {
        return repository.getCard(cardId)
    }
}