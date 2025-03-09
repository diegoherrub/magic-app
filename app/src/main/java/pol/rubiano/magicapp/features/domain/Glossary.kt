package pol.rubiano.magicapp.features.domain

data class GlossaryTerm(
    val key: String,
    val category: String,
    val term: String,
    val definition: String,
    val imageResId: Int? = null
)

sealed class GlossaryItem {
    data class Header(val category: String) : GlossaryItem()
    data class Entry(val glossaryTerm: GlossaryTerm) : GlossaryItem()
}
