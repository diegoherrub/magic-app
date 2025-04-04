package pol.rubiano.magicapp.app.domain.models

import pol.rubiano.magicapp.R

sealed class CardCategory(val title: String) {
    data object Creatures : CardCategory(R.string.type_creature.toString())
    data object Lands : CardCategory(R.string.type_land.toString())
    data object Spells : CardCategory(R.string.type_spell.toString())
}