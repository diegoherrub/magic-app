package pol.rubiano.magicapp.features.data.remote

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.app.common.extensions.apiCall
import pol.rubiano.magicapp.features.domain.models.Scryfall

@Single
class ScryfallDataSource(
    private val service: ScryfallService
) {
    suspend fun getsearchCards(query: String): Result<Scryfall> {
        return apiCall {
            service.searchCards(query)
        }.map {
            it.toModel()
        }
    }

    suspend fun getNextPage(nextPageUrl: String): Result<Scryfall> {
        return apiCall {
            service.getNextPage(nextPageUrl)
        }.map {
            it.toModel()
        }
    }
}
