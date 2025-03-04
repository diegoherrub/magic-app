package pol.rubiano.magicapp.app.domain.entities

fun Card.Legalities.toLegalityItemList(): List<LegalityItem> {
    return listOf(
        LegalityItem("standard", this.standard),
        LegalityItem("future", this.future),
        LegalityItem("historic", this.historic),
        LegalityItem("timeless", this.timeless),
        LegalityItem("gladiator", this.gladiator),
        LegalityItem("pioneer", this.pioneer),
        LegalityItem("explorer", this.explorer),
        LegalityItem("modern", this.modern),
        LegalityItem("legacy", this.legacy),
        LegalityItem("pauper", this.pauper),
        LegalityItem("vintage", this.vintage),
        LegalityItem("penny", this.penny),
        LegalityItem("commander", this.commander),
        LegalityItem("oathbreaker", this.oathbreaker),
        LegalityItem("standardBrawl", this.standardBrawl),
        LegalityItem("brawl", this.brawl),
        LegalityItem("alchemy", this.alchemy),
        LegalityItem("paupercommander", this.pauperCommander),
        LegalityItem("duel", this.duel),
        LegalityItem("oldschool", this.oldschool),
        LegalityItem("premodern", this.premodern),
        LegalityItem("predh", this.predh),
    )
}
