package pol.rubiano.magicapp.features.data.remote

import android.util.Log
import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.domain.Scryfall
import pol.rubiano.magicapp.features.domain.repositories.ScryfallRepository

@Single
class ScryfallDataRepository(
    private val remote: ScryfallDataSource
) : ScryfallRepository {

    override suspend fun getScryfallSearch(query: String): Result<Scryfall> {
        return try {
            val resultScryfallDataRepository = remote.getsearchCards(query)
            Log.d("@POL", "resultScryfallDataRepository: $resultScryfallDataRepository")
            return resultScryfallDataRepository
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

//    override suspend fun getNextPage(nextPageUrl: String): Result<Scryfall> {
//        return try {
//            remote.getNextPage(nextPageUrl)
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
}