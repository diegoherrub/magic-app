package pol.rubiano.magicapp.features.data.remote

import android.util.Log
import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.domain.Scryfall

@Single
class ScryfallDataSource(
    private val service: ScryfallService
) {
    suspend fun getsearchCards(query: String): Result<Scryfall> {
        return try {
            val response = service.searchCards(query)
            if (response.isSuccessful) {
                response.body()?.let { apiModel ->
                    Result.success(apiModel.toModel())
                } ?: Result.failure(Exception("Respuesta vacía"))
            } else {
                Result.failure(Exception("Error code: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getNextPage(nextPageUrl: String): Result<Scryfall> {
        return try {
            val response = service.getNextPage(nextPageUrl)
            if (response.isSuccessful) {
                response.body()?.let { apiModel ->
                    Result.success(apiModel.toModel())
                } ?: Result.failure(Exception("Respuesta vacía"))
            } else {
                Result.failure(Exception("Error code: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
