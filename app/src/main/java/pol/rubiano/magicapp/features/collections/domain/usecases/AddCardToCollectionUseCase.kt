package pol.rubiano.magicapp.features.collections.domain.usecases

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.collections.domain.repositories.CollectionsRepository

@Single
class AddCardToCollectionUseCase(
    private val repository: CollectionsRepository
) {
    suspend operator fun invoke(collectionName: String, cardId: String){
       return repository.addCardToCollection(collectionName, cardId)
    }
}