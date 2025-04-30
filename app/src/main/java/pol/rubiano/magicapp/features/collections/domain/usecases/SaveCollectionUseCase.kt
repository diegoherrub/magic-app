package pol.rubiano.magicapp.features.collections.domain.usecases

import android.util.Log
import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.collections.domain.repositories.CollectionsRepository
import pol.rubiano.magicapp.features.collections.domain.Collection

@Single
class SaveCollectionUseCase(
    private val repository: CollectionsRepository
) {
    suspend operator fun invoke(collection: Collection) {
        Log.d("@pol", "collection de usecase -> $collection")
        repository.saveCollection(collection)
    }
}
