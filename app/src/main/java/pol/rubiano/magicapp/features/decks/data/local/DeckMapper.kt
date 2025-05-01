package pol.rubiano.magicapp.features.decks.data.local

import pol.rubiano.magicapp.features.decks.domain.models.CardInDeck
import pol.rubiano.magicapp.features.decks.domain.models.Deck

fun DeckEntity.toDeck(): Deck {
    return Deck(
        this.id,
        this.name,
        this.description,
        this.colors,
        this.cards
    )
}

fun Deck.toDeckEntity(): DeckEntity {
    return DeckEntity(
        this.id,
        this.name,
        this.description,
        this.colors,
        this.cards
    )
}

fun CardInDeckEntity.toCardInDeck(): CardInDeck {
    return CardInDeck(
        this.cardId,
        this.deckId,
        this.mainBoardCopies,
        this.sideBoardCopies,
        this.mayBeBoardCopies
    )
}

fun CardInDeck.toEntity(): CardInDeckEntity {
    return CardInDeckEntity(
        this.cardId,
        this.deckId,
        this.mainBoardCopies,
        this.sideBoardCopies,
        this.mayBeBoardCopies
    )
}
