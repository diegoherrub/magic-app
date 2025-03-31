package pol.rubiano.magicapp.features.domain.usecases

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.app.domain.models.Card
import pol.rubiano.magicapp.app.domain.repositories.CardRepository

@Single
class GetRandomCardUseCase(
    private val cardRepository: CardRepository
) {
    suspend operator fun invoke(): Result<Card> {
        return cardRepository.getRandomCard()
    }
}