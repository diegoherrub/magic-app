package pol.rubiano.magicapp.features.collections.domain.usecases

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.collections.domain.repositories.CollectionsRepository
import pol.rubiano.magicapp.features.collections.domain.CardInCollection

@Single
class GetCardsOfCollectionUseCase(
    private val repository: CollectionsRepository
) {
    suspend operator fun invoke(collectionName: String): List<CardInCollection> {
        return repository.getCardsOfCollection(collectionName)
    }
}