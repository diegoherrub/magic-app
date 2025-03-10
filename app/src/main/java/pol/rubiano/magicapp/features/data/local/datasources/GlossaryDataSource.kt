package pol.rubiano.magicapp.features.data.local.datasources

import android.content.Context
import org.xmlpull.v1.XmlPullParser
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.data.mapManaSymbols
import pol.rubiano.magicapp.features.domain.GlossaryItem
import pol.rubiano.magicapp.features.domain.GlossaryTerm

fun loadGlossaryFromXml(
    context: Context
): List<GlossaryTerm> {
    val glossaryList = mutableListOf<GlossaryTerm>()
    val parser = context.resources.getXml(R.xml.glossary)
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

            glossaryList.add(
                GlossaryTerm(
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
    return glossaryList
}

fun groupGlossaryTerms(terms: List<GlossaryTerm>): List<GlossaryItem> {
    val grouped = terms.groupBy { it.category }
    val itemList = mutableListOf<GlossaryItem>()
    for (category in grouped.keys.sorted()) {
        itemList.add(GlossaryItem.Header(category))
        grouped[category]?.forEach { term ->
            itemList.add(GlossaryItem.Entry(term))
        }
    }
    return itemList
}
