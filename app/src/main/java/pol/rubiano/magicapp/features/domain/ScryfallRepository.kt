package pol.rubiano.magicapp.features.domain

interface ScryfallRepository {
    suspend fun getScryfallSearch(query: String): Result<Scryfall>
    suspend fun getNextPage(nextPageUrl: String): Result<Scryfall>
}