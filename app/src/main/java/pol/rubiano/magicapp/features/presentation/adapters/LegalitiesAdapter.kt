package pol.rubiano.magicapp.features.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.presentation.AppDiffUtil
import pol.rubiano.magicapp.features.domain.models.Legality
import pol.rubiano.magicapp.features.presentation.adapters.viewholders.LegalitiesViewHolder

class LegalitiesAdapter() : ListAdapter<Legality, LegalitiesViewHolder> (
    AppDiffUtil<Legality>(
        itemSame = { old, new -> old == new },
        contentSame = { old, new -> old == new }
    )
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LegalitiesViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.view_common_section_list_type_item, parent, false)
        return LegalitiesViewHolder(view)
    }

    override fun onBindViewHolder(holder: LegalitiesViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}