package pol.rubiano.magicapp.features.collections.data

import pol.rubiano.magicapp.features.collections.domain.Collection

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