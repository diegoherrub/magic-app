package pol.rubiano.magicapp.features.domain

data class LegalityTerm(
    val key: String,
    val category: String,
    val term: String,
    val definition: CharSequence,
    val imageResId: Int? = null
)

sealed class LegalityItem {
    data class Header(val category: String, var isExpanded: Boolean = true) : LegalityItem()
    data class Entry(val legalityTerm: LegalityTerm) : LegalityItem()
}