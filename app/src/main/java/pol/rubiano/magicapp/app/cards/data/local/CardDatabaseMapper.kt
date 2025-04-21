package pol.rubiano.magicapp.app.cards.data.local

import pol.rubiano.magicapp.app.domain.models.Card

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
