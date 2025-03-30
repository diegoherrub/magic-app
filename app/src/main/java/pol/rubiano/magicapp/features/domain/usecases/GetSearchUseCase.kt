package pol.rubiano.magicapp.features.domain.usecases

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.domain.entities.Scryfall
import pol.rubiano.magicapp.features.domain.repositories.ScryfallRepository

@Single
class GetSearchUseCase(
    private val scryfallRepository: ScryfallRepository
) {
    suspend operator fun invoke(query: String): Result<Scryfall> {
        return scryfallRepository.getScryfallSearch(query)
    }

    suspend fun loadNextPage(nextPageUrl: String): Result<Scryfall> {
        return scryfallRepository.getNextPage(nextPageUrl)
    }
}