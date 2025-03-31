package pol.rubiano.magicapp.app.domain.models

import pol.rubiano.magicapp.R

sealed class CardCategory(val title: String) {
    object Creatures : CardCategory(R.string.type_creature.toString())
    object Lands : CardCategory(R.string.type_land.toString())
    object Spells : CardCategory(R.string.type_spell.toString())
}