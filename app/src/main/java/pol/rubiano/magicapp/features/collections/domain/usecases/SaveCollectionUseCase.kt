package pol.rubiano.magicapp.features.collections.domain.usecases

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.collections.domain.repositories.CollectionsRepository
import pol.rubiano.magicapp.features.collections.domain.models.Collection

@Single
class SaveCollectionUseCase(
    private val repository: CollectionsRepository
) {
    suspend operator fun invoke(collection: Collection) {
        repository.saveCollection(collection)
    }
}
