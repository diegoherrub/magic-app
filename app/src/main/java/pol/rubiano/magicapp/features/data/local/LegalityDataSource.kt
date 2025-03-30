package pol.rubiano.magicapp.features.data.local

import pol.rubiano.magicapp.features.domain.entities.Legality
import android.content.Context
import org.xmlpull.v1.XmlPullParser
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.data.mapManaSymbols

fun loadLegalitiesFromXml(
    context: Context
): List<Legality> {
    val legalitiesList = mutableListOf<Legality>()
    val parser = context.resources.getXml(R.xml.legalities)
    var eventType = parser.eventType

    while (eventType != XmlPullParser.END_DOCUMENT) {
        if (eventType == XmlPullParser.START_TAG && parser.name == "entry") {
            val imageResId = parser.getAttributeResourceValue(null, "image", 0)
            val termResId = parser.getAttributeResourceValue(null, "term", 0)
            val informationResId = parser.getAttributeResourceValue(null, "information", 0)

            val term = context.getString(termResId)
            val information =
                mapManaSymbols(context, context.getString(informationResId)).toString()

            legalitiesList.add(
                Legality(
                    icon = imageResId,
                    term = term,
                    information = information
                )
            )
        }
        eventType = parser.next()
    }
    return legalitiesList
}