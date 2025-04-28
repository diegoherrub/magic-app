package pol.rubiano.magicapp.features.cards.data.local

import pol.rubiano.magicapp.features.cards.domain.models.Card
import pol.rubiano.magicapp.features.collections.data.local.CardInCollectionEntity
import pol.rubiano.magicapp.features.collections.domain.CardInCollection

fun Card.toEntity() : CardEntity {
    return CardEntity(
        this.id,
        this.oracleId,
        this.name,
        this.typeLine,
        this.rarity,
        this.oracleText,
        this.loyalty,
        this.power,
        this.toughness,
        this.cmc,
        this.manaCost,
        this.producedMana,
        this.releasedAt,
        this.imageSmall,
        this.imageCrop,
        this.set,
        this.setName,
        this.setType,
        this.collectorNumber,
        this.frontFace,
        this.backFace,
        this.legalities
    )
}

fun CardEntity.toDomain(): Card {
    return Card(
        this.id,
        this.oracleId,
        this.name,
        this.typeLine,
        this.rarity,
        this.oracleText,
        this.loyalty,
        this.power,
        this.toughness,
        this.cmc,
        this.manaCost,
        this.producedMana,
        this.releasedAt,
        this.imageSmall,
        this.imageCrop,
        this.set,
        this.setName,
        this.setType,
        this.collectorNumber,
        this.frontFace,
        this.backFace,
        this.legalities
    )
}

fun CardInCollection.toEntity() : CardInCollectionEntity {
    return CardInCollectionEntity(
        this.cardId,
        this.collectionName,
        this.copies,
    )
}

fun CardInCollection.toDomain(): CardInCollection {
    return CardInCollection(
        this.cardId,
        this.collectionName,
        this.copies,
    )
}