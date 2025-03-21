package pol.rubiano.magicapp.features.data.local

import pol.rubiano.magicapp.features.domain.LegalityItem
import pol.rubiano.magicapp.features.domain.LegalityTerm
import android.content.Context
import org.xmlpull.v1.XmlPullParser
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.data.mapManaSymbols

fun loadLegalityFromXml(
    context: Context
): List<LegalityTerm> {
    val legalityList = mutableListOf<LegalityTerm>()
    val parser = context.resources.getXml(R.xml.legality)
    var eventType = parser.eventType

    while (eventType != XmlPullParser.END_DOCUMENT) {
        if (eventType == XmlPullParser.START_TAG && parser.name == "entry") {
            val key = parser.getAttributeValue(null, "key")
            val category = parser.getAttributeValue(null, "category")
            val imageResId = parser.getAttributeResourceValue(null, "image", 0)
            val termResId = parser.getAttributeResourceValue(null, "term", 0)
            val definitionResId = parser.getAttributeResourceValue(null, "definition", 0)
            val term = if (termResId != 0) context.getString(termResId)
            else parser.getAttributeValue(null, "term")
            val definition = if (definitionResId != 0)
                mapManaSymbols(context, context.getString(definitionResId))
            else parser.getAttributeValue(null, "definition")

            legalityList.add(
                LegalityTerm(
                    key = key,
                    category = category,
                    term = term,
                    definition = definition,
                    imageResId = if (imageResId != 0) imageResId else null
                )
            )
        }
        eventType = parser.next()
    }
    return legalityList
}

fun groupLegalityTerms(terms: List<LegalityTerm>): List<LegalityItem> {
    val grouped = terms.groupBy { it.category }
    val itemList = mutableListOf<LegalityItem>()
    for (category in grouped.keys.sorted()) {
        itemList.add(LegalityItem.Header(category))
        grouped[category]?.forEach { term ->
            itemList.add(LegalityItem.Entry(term))
        }
    }
    return itemList
}
