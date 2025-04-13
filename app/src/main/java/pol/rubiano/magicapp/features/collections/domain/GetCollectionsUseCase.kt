package pol.rubiano.magicapp.features.collections.domain

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.collections.data.CollectionsRepository

@Single
class GetCollectionsUseCase(
    private val repository: CollectionsRepository
) {
    suspend operator fun invoke(): List<Collection> {
        return repository.getCollections()
    }
}