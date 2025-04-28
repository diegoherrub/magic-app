package pol.rubiano.magicapp.features.cards.domain.usecases

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.cards.domain.models.Card
import pol.rubiano.magicapp.features.cards.domain.repositories.CardRepository

@Single
class AddCardToCollectionUseCase(
    private val cardRepository: CardRepository
) {
    suspend operator fun invoke(card: Card, collectionName: String) {
        cardRepository.addCardToCollection(card, collectionName)
    }
}