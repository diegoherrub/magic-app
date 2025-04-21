package pol.rubiano.magicapp.features.domain.usecases

import android.util.Log
import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.domain.models.Scryfall
import pol.rubiano.magicapp.features.domain.repositories.ScryfallRepository

@Single
class GetSearchUseCase(
    private val scryfallRepository: ScryfallRepository
) {
    suspend operator fun invoke(query: String): Result<Scryfall> {
        Log.d("@pol", "GetSearchUseCase().invoke -> query: $query")
        return scryfallRepository.getScryfallSearch(query)
    }

    suspend fun loadNextPage(nextPageUrl: String): Result<Scryfall> {
        Log.d("@pol", "GetSearchUseCase().loadNextPage -> nextPageUrl: $nextPageUrl")
        return scryfallRepository.getNextPage(nextPageUrl)
    }
}