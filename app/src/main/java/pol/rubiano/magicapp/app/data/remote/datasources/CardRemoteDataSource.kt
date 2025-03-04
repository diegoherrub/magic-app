package pol.rubiano.magicapp.app.data.remote.datasources

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.app.data.remote.extensions.apiCall
import pol.rubiano.magicapp.app.data.remote.services.CardService
import pol.rubiano.magicapp.app.data.remote.apimappers.toModel
import pol.rubiano.magicapp.app.domain.entities.Card

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
