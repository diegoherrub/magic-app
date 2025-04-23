package pol.rubiano.magicapp.features.cards.presentation.viewholders

import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import pol.rubiano.magicapp.R
import androidx.core.graphics.toColorInt

class LegalitiesViewHolder(
    private val view: View
) : RecyclerView.ViewHolder(view) {

    private val tvLegality: MaterialTextView = itemView.findViewById(R.id.tvLegality)

    fun bind(format: String, legality: String) {
        val context = view.context

        val textRes = when (format.lowercase()) {
            "standard" -> R.string.standard
            "modern" -> R.string.modern
            "commander" -> R.string.commander
            "brawl" -> R.string.brawl
            "pioneer" -> R.string.pioneer
            "legacy" -> R.string.legacy
            "vintage" -> R.string.vintage
            "pauper" -> R.string.pauper
            "paupercommander" -> R.string.paupercommander
            "alchemy" -> R.string.alchemy
            "historic" -> R.string.historic
            "timeless" -> R.string.timeless
            "explorer" -> R.string.explorer
            "duel" -> R.string.duel
            "gladiator" -> R.string.gladiator
            "standardbrawl" -> R.string.standardbrawl
            "penny" -> R.string.penny
            "oathbreaker" -> R.string.oathbreaker
            "oldschool" -> R.string.oldschool
            "premodern" -> R.string.premodern
            "future" -> R.string.future
            "predh" -> R.string.predh
            else -> null
        }

        tvLegality.text = textRes?.let { context.getString(it) } ?: format

        if (legality == "legal") {
            (tvLegality.background as GradientDrawable).setColor("#CCFFCC".toColorInt())
        } else if (legality == "not_legal") {
            (tvLegality.background as GradientDrawable).setColor("#FFCCCC".toColorInt())
        } else {
            (tvLegality.background as GradientDrawable).setColor("#CCCCCC".toColorInt())
        }
    }
}
