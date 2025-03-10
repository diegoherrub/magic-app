package pol.rubiano.magicapp.features.domain

data class GlossaryTerm(
    val key: String,
    val category: String,
    val term: String,
    val definition: CharSequence,
    val imageResId: Int? = null
)

sealed class GlossaryItem {
    data class Header(val category: String, var isExpanded: Boolean = true) : GlossaryItem()
    data class Entry(val glossaryTerm: GlossaryTerm) : GlossaryItem()
}

