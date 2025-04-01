package pol.rubiano.magicapp.app.domain

import android.util.Log
import pol.rubiano.magicapp.app.domain.models.Card

object DeckStatsAnalyzer {

    fun getUniqueColors(cards: List<Card>): List<String> {
        val colorSymbols = mutableSetOf<String>()

        cards.forEach { card ->
            extractColorsFromManaCost(card.manaCost)?.let { colorSymbols += it }

            card.frontFace?.faceManaCost?.let {
                extractColorsFromManaCost(it)?.let { colorSymbols += it }
            }

            card.backFace?.faceManaCost?.let {
                extractColorsFromManaCost(it)?.let { colorSymbols += it }
            }
        }

        return colorSymbols.sorted()
    }

    private fun extractColorsFromManaCost(manaCost: String?): Set<String>? {
        if (manaCost.isNullOrBlank()) return null

        // Regex to capture mana symbols like {W}, {U}, {B}, {R}, {G}
        val regex = Regex("""\{(\w+)\}""")
        Log.d("RegexCheck", "Using safe regex for mana parsing.")

        return regex.findAll(manaCost)
            .mapNotNull { match ->
                val symbol = match.groupValues[1]
                when (symbol.uppercase()) {
                    "W", "U", "B", "R", "G" -> symbol.uppercase()
                    else -> null // Ignore {C}, {X}, {1}, etc.
                }
            }.toSet()
    }
}
