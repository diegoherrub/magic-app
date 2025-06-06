package pol.rubiano.magicapp.features.cards.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface CardService {
    @GET("cards/random")
    suspend fun getRandomCard(): Response<CardApiModel>
}