package pol.rubiano.magicapp.features.collections.data.local

import pol.rubiano.magicapp.features.collections.domain.models.CardInCollection
import pol.rubiano.magicapp.features.collections.domain.models.Collection

fun CollectionEntity.toCollection(): Collection {
    return Collection(
        this.name,
        this.order,
        this.cards
    )
}

fun Collection.toEntity(): CollectionEntity {
    return CollectionEntity(
        this.name,
        this.order,
        this.cards
    )
}

fun CardInCollectionEntity.toCardInCollection(): CardInCollection {
    return CardInCollection(
        this.cardId,
        this.collectionName,
        this.copies
    )
}

fun CardInCollection.toEntity(): CardInCollectionEntity {
    return CardInCollectionEntity(
        this.cardId,
        this.collectionName,
        this.copies
    )
}