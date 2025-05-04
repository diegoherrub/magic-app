package pol.rubiano.magicapp.features.decks.data

import android.content.Context
import org.koin.core.annotation.Single
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.features.decks.data.local.DeckEntity
import pol.rubiano.magicapp.features.decks.data.local.DeckLocalDataSource
import pol.rubiano.magicapp.features.decks.data.local.toDeck
import pol.rubiano.magicapp.features.decks.data.local.toDeckEntity
import pol.rubiano.magicapp.features.decks.domain.models.CardInDeck
import pol.rubiano.magicapp.features.decks.domain.models.Deck
import pol.rubiano.magicapp.features.decks.domain.repositories.DecksRepository
import java.util.UUID

@Single
class DecksDataRepository(
    private val local: DeckLocalDataSource,
    private val context: Context
) : DecksRepository {

    override suspend fun saveDeck(deck: Deck): Deck {
        val defaultName = context.getString(R.string.str_newDeck)
        var deckNameToSave = deck.name
        val deckDescriptionToSave = deck.description
        val decksCount = local.getDecksCount()
        val nDecks = decksCount + 1

        if (deck.name == defaultName) {
            deckNameToSave = "$defaultName - $nDecks"
        } else {
            val existingDeck = local.getDeckByName(deckNameToSave)
            if (existingDeck != null) {
                deckNameToSave += " - $nDecks"
            }
        }

        val deckEntity = DeckEntity(
            id = UUID.randomUUID().toString(),
            name = deckNameToSave,
            description = deckDescriptionToSave,
            colors = deck.colors,
            cards = deck.cards
        )

        local.saveDeck(deckEntity)
        return deckEntity.toDeck()
    }

    override suspend fun getDecks(): List<Deck> {
        val deckEntities = local.getDecks()
        return deckEntities.map { it.toDeck() }
    }

    override suspend fun getDeckById(deckId: String): Deck {
        return local.getDeckById(deckId)!!
    }

    override suspend fun saveCardInDeck(cardId: String, deckId: String): Deck {
        return local.saveCardInDeck(cardId, deckId)!!
    }

    override suspend fun getCardsInDeck(deckId: String): List<CardInDeck> {
        return local.getCardsInDeck(deckId)
    }
}
