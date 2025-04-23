package pol.rubiano.magicapp.features.cards.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.features.cards.domain.models.LegalityItem
import pol.rubiano.magicapp.app.presentation.AppDiffUtil
import pol.rubiano.magicapp.features.cards.presentation.viewholders.LegalitiesViewHolder

class LegalitiesAdapter : ListAdapter<LegalityItem, LegalitiesViewHolder>(
    AppDiffUtil<LegalityItem>(
        itemSame = { old, new -> old.format == new.format },
        contentSame = {old, new -> old == new}
    )
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LegalitiesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardlegalities_item_view, parent, false)
        return LegalitiesViewHolder(view)
    }

    override fun onBindViewHolder(holder: LegalitiesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item.format, item.legality)
    }
}