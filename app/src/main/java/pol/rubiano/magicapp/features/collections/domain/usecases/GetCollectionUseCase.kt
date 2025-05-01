package pol.rubiano.magicapp.features.collections.domain.usecases

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.collections.domain.repositories.CollectionsRepository
import pol.rubiano.magicapp.features.collections.domain.models.Collection

@Single
class GetCollectionUseCase(
    private val repository: CollectionsRepository
) {
    suspend operator fun invoke(collectionName: String): Collection {
        return repository.getCollectionByName(collectionName)
    }
}