package pol.rubiano.magicapp.app.domain.repositories

import pol.rubiano.magicapp.app.domain.entities.Card

interface CardRepository {
    suspend fun getRandomCard(): Result<Card>
}