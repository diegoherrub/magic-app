package pol.rubiano.magicapp.features.search.domain.usecases

import android.util.Log
import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.search.domain.models.Scryfall
import pol.rubiano.magicapp.features.search.domain.repositories.ScryfallRepository

@Single
class GetNextPageUseCase(
    private val scryfallRepository: ScryfallRepository
) {
    suspend operator fun invoke(nextPageUrl: String): Result<Scryfall> {
        return scryfallRepository.getNextPage(nextPageUrl)
    }
}