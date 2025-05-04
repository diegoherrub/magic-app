package pol.rubiano.magicapp.features.search.domain.usecases

import android.util.Log
import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.search.domain.models.Scryfall
import pol.rubiano.magicapp.features.search.domain.repositories.ScryfallRepository

@Single
class GetSearchUseCase(
    private val scryfallRepository: ScryfallRepository
) {
    suspend operator fun invoke(query: String): Result<Scryfall> {
        return scryfallRepository.getScryfallSearch(query)
    }
}