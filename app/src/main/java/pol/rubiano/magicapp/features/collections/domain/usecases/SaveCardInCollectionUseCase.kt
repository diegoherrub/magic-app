package pol.rubiano.magicapp.features.collections.domain.usecases

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.collections.domain.models.Collection
import pol.rubiano.magicapp.features.collections.domain.repositories.CollectionsRepository

@Single
class SaveCardInCollectionUseCase(
    private val collectionRepository: CollectionsRepository
) {
    suspend operator fun invoke(cardId: String, collectionName:String): Collection {
        return collectionRepository.saveCardInCollection(cardId, collectionName)
    }
}
