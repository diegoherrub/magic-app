package pol.rubiano.magicapp.features.cards.domain.extensions

import pol.rubiano.magicapp.features.cards.domain.models.Card
import pol.rubiano.magicapp.features.cards.domain.models.LegalityItem

fun Card.Legalities.toLegalityItemList(): List<LegalityItem?> {
    return listOf(
        this.standard?.let { LegalityItem("standard", it) },
        this.future?.let { LegalityItem("future", it) },
        this.historic?.let { LegalityItem("historic", it) },
        this.timeless?.let { LegalityItem("timeless", it) },
        this.gladiator?.let { LegalityItem("gladiator", it) },
        this.pioneer?.let { LegalityItem("pioneer", it) },
        this.explorer?.let { LegalityItem("explorer", it) },
        this.modern?.let { LegalityItem("modern", it) },
        this.legacy?.let { LegalityItem("legacy", it) },
        this.pauper?.let { LegalityItem("pauper", it) },
        this.vintage?.let { LegalityItem("vintage", it) },
        this.penny?.let { LegalityItem("penny", it) },
        this.commander?.let { LegalityItem("commander", it) },
        this.oathbreaker?.let { LegalityItem("oathbreaker", it) },
        this.standardBrawl?.let { LegalityItem("standardBrawl", it) },
        this.brawl?.let { LegalityItem("brawl", it) },
        this.alchemy?.let { LegalityItem("alchemy", it) },
        this.pauperCommander?.let { LegalityItem("paupercommander", it) },
        this.duel?.let { LegalityItem("duel", it) },
        this.oldschool?.let { LegalityItem("oldschool", it) },
        this.premodern?.let { LegalityItem("premodern", it) },
        this.predh?.let { LegalityItem("predh", it) },
    )
}
