package pol.rubiano.magicapp.features.search.domain.repositories

import pol.rubiano.magicapp.features.search.domain.models.Scryfall

interface ScryfallRepository {
    suspend fun getScryfallSearch(query: String): Result<Scryfall>
    suspend fun getNextPage(nextPageUrl: String): Result<Scryfall>
}