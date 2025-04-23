package pol.rubiano.magicapp.features.cards.data.remote

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.app.common.extensions.apiCall
import pol.rubiano.magicapp.features.cards.domain.models.Card

@Single
class CardRemoteDataSource(
    private val service: CardService
) {
    suspend fun getRandomCard(): Result<Card> {
        return apiCall {
            service.getRandomCard()
        }.map {
            it.toModel()
        }
    }
}
