package pol.rubiano.magicapp.features.data.remote

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.domain.Scryfall
import pol.rubiano.magicapp.features.domain.ScryfallRepository

@Single
class ScryfallDataRepository(
    private val remote: ScryfallDataSource
) : ScryfallRepository {

    override suspend fun getScryfallSearch(query: String): Result<Scryfall> {
        return remote.getsearchCards(query)
    }

    override suspend fun getNextPage(nextPageUrl: String): Result<Scryfall> {
        return remote.getNextPage(nextPageUrl)
    }
}