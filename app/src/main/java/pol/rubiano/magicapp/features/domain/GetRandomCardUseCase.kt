package pol.rubiano.magicapp.features.domain

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.app.domain.Card
import pol.rubiano.magicapp.app.domain.CardRepository

@Single
class GetRandomCardUseCase(
    private val cardRepository: CardRepository
) {
    suspend operator fun invoke(): Result<Card> {
        return cardRepository.getRandomCard()
    }
}