package pol.rubiano.magicapp.features.domain.repositories

import pol.rubiano.magicapp.features.domain.Scryfall

interface ScryfallRepository {
    suspend fun getScryfallSearch(query: String): Result<Scryfall>
    //suspend fun getNextPage(nextPageUrl: String): Result<Scryfall>
}