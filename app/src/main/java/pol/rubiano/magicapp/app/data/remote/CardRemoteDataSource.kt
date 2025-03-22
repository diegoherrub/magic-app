package pol.rubiano.magicapp.app.data.remote

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.app.data.remote.extensions.apiCall
import pol.rubiano.magicapp.app.domain.Card

@Single
class CardRemoteDataSource(
    private val service: CardService
) {
    suspend fun getRandomCard(): Result<Card> {
        return apiCall {
            service.getRandomCard()
        }.map { it.toModel() }
    }
}
