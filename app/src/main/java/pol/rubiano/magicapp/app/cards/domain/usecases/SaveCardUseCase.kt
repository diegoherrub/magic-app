package pol.rubiano.magicapp.app.cards.domain.usecases

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.app.cards.domain.repositories.CardsRepository
import pol.rubiano.magicapp.app.domain.models.Card

@Single
class SaveCardUseCase(
    private val repository: CardsRepository
){
    suspend operator fun invoke(card: Card) {
        repository.saveCard(card)
    }
}