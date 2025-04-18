package pol.rubiano.magicapp.features.collections.domain.usescase

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.collections.data.CollectionsRepository

@Single
class GetCardsInCollectionUseCase(
    private val repository: CollectionsRepository
) {
//    suspend operator fun invoke(): List<Card> {
//        return repository.getCardsInCollection()
//    }
}