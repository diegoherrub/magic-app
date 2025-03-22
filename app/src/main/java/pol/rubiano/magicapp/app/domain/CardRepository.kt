package pol.rubiano.magicapp.app.domain

interface CardRepository {
    suspend fun getRandomCard(): Result<Card>
}