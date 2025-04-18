package pol.rubiano.magicapp.features.collections.domain.usescase

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.collections.data.CollectionsRepository

@Single
class AddCardToCollectionUseCase(
    private val repository: CollectionsRepository
) {
    suspend operator fun invoke(collectionName: String, cardId: String){
       return repository.addCardToCollection(collectionName, cardId)
    }
}