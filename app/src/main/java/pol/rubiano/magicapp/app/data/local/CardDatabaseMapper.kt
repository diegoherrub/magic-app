package pol.rubiano.magicapp.app.data.local

import pol.rubiano.magicapp.app.domain.entities.Card

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
        this.imageNormal,
        this.set,
        this.setName,
        this.setType,
        this.collectorNumber,
        this.frontFace,
        this.backFace,
        this.legalities
    )
}