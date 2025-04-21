package pol.rubiano.magicapp.app.cards.domain.usecases

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.app.cards.domain.repositories.CardsRepository
import pol.rubiano.magicapp.app.domain.models.Card

@Single
class GetCardUseCase(
    private val repository: CardsRepository
) {
    suspend operator fun invoke(cardId: String): Card? {
        return repository.getCardById(cardId)
    }
}