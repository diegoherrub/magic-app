package pol.rubiano.magicapp.features.data.local

import pol.rubiano.magicapp.features.domain.models.Deck

fun DeckEntity.toDeck(): Deck {
    return Deck(
        this.id,
        this.name,
        this.description,
        this.colors,
        this.cardIds,
        this.sideBoard,
        this.maybeBoard
    )
}

fun Deck.toEntity(): DeckEntity {
    return DeckEntity(
        this.id,
        this.name,
        this.description,
        this.colors,
        this.cardIds,
        this.sideBoard,
        this.maybeBoard
    )
}
