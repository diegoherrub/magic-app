package pol.rubiano.magicapp.features.data.local

import android.content.Context
import org.xmlpull.v1.XmlPullParser
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.data.mapManaSymbols
import pol.rubiano.magicapp.features.domain.models.Keyword

fun loadKeywordsFromXml(
    context: Context
): List<Keyword> {
    val keywordsList = mutableListOf<Keyword>()
    val parser = context.resources.getXml(R.xml.keywords)
    var eventType = parser.eventType

    while (eventType != XmlPullParser.END_DOCUMENT) {
        if (eventType == XmlPullParser.START_TAG && parser.name == "entry") {
            val imageResId = parser.getAttributeResourceValue(null, "image", 0)
            val termResId = parser.getAttributeResourceValue(null, "term", 0)
            val informationResId = parser.getAttributeResourceValue(null, "information", 0)

            val term = context.getString(termResId)
            val information =
                mapManaSymbols(context, context.getString(informationResId)).toString()

            keywordsList.add(
                Keyword(
                    icon = imageResId,
                    term = term,
                    information = information
                )
            )
        }
        eventType = parser.next()
    }
    return keywordsList
}