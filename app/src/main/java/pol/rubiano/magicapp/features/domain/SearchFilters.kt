package pol.rubiano.magicapp.features.domain

data class Filter(
    val name: String,
    val options: List<String>
)

val filters = listOf(
    Filter("Raridades", listOf("common", "uncommon", "rare", "mythic")),
    Filter("Colores", listOf("white", "blue", "black", "red", "green")),
    Filter("Tipos", listOf("artifact", "creature", "enchantment", "instant", "sorcery"))
)
