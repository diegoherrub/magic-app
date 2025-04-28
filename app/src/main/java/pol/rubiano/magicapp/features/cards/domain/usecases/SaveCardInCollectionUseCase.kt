package pol.rubiano.magicapp.features.cards.domain.usecases

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.cards.domain.repositories.CardRepository
import pol.rubiano.magicapp.features.collections.domain.CardInCollection

@Single
class SaveCardInCollectionUseCase(
    private val cardRepository: CardRepository
) {
    suspend operator fun invoke(cardInCollection: CardInCollection) {
        //cardRepository.updateCardsInCollection(cardInCollection)
    }
}