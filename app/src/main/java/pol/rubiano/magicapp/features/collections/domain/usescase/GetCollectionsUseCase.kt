package pol.rubiano.magicapp.features.collections.domain.usescase

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.collections.data.CollectionsRepository
import pol.rubiano.magicapp.features.collections.domain.Collection

@Single
class GetCollectionsUseCase(
    private val repository: CollectionsRepository
) {
    suspend operator fun invoke(): List<Collection> {
        return repository.getCollections()
    }
}