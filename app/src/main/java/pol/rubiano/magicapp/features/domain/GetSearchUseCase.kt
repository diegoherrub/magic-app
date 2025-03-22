package pol.rubiano.magicapp.features.domain

import org.koin.core.annotation.Single

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