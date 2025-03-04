package pol.rubiano.magicapp.app.presentation.legalities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.domain.entities.LegalityItem

class LegalitiesAdapter : ListAdapter<LegalityItem, LegalitiesViewHolder>(LegalitiesDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LegalitiesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.legalities_item_view, parent, false)
        return LegalitiesViewHolder(view)
    }

    override fun onBindViewHolder(holder: LegalitiesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item.format, item.legality)
    }
}