package pol.rubiano.magicapp.features.search.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ScryfallService {
    @GET("cards/search")
    suspend fun searchCards(@Query("q") query: String): Response<ScryfallApiModel>

    @GET
    suspend fun getNextPage(@Url url: String): Response<ScryfallApiModel>
}