package pol.rubiano.magicapp.features.decks.domain.assets

import android.util.Log
import pol.rubiano.magicapp.features.cards.domain.models.Card

object DeckStatsAnalyzer {

    fun getUniqueColors(cards: List<Card?>): List<String> {
        val colorSymbols = mutableSetOf<String>()

        cards.forEach { card ->
            if (card != null) {
                extractColorsFromManaCost(card.manaCost)?.let { colorSymbols += it }
            }

            if (card != null) {
                card.frontFace?.faceManaCost?.let {
                    extractColorsFromManaCost(it)?.let { colorSymbols += it }
                }
            }

            if (card != null) {
                card.backFace?.faceManaCost?.let {
                    extractColorsFromManaCost(it)?.let { colorSymbols += it }
                }
            }
        }

        return colorSymbols.sorted()
    }

    private fun extractColorsFromManaCost(manaCost: String?): Set<String>? {
        if (manaCost.isNullOrBlank()) return null

        val regex = Regex("""\{(\w+)\}""")
        Log.d("RegexCheck", "Using safe regex for mana parsing.")

        return regex.findAll(manaCost)
            .mapNotNull { match ->
                val symbol = match.groupValues[1]
                when (symbol.uppercase()) {
                    "W", "U", "B", "R", "G" -> symbol.uppercase()
                    else -> null
                }
            }.toSet()
    }
}
