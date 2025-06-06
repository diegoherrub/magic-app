package pol.rubiano.magicapp.features.cards.domain.usecases

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.cards.domain.repositories.CardRepository
import pol.rubiano.magicapp.features.cards.domain.models.Card

@Single
class SaveCardUseCase(
    private val cardRepository: CardRepository
) {
    suspend operator fun invoke(card: Card) {
        cardRepository.saveCard(card)
    }
}