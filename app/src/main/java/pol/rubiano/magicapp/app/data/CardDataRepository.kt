package pol.rubiano.magicapp.app.data

import org.koin.core.annotation.Single
import pol.rubiano.magicapp.app.data.remote.datasources.CardRemoteDataSource
import pol.rubiano.magicapp.app.domain.entities.Card
import pol.rubiano.magicapp.app.domain.repositories.CardRepository

@Single
class CardDataRepository(
    private val remote: CardRemoteDataSource
) : CardRepository {

    override suspend fun getRandomCard(): Result<Card> {
        return remote.getRandomCard()
    }
}