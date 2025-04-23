package pol.rubiano.magicapp.features.magic.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.presentation.AppDiffUtil
import pol.rubiano.magicapp.features.magic.domain.modules.LegalFormat
import pol.rubiano.magicapp.features.magic.presentation.viewholders.LegalFormatsViewHolder

class LegalFormatsAdapter : ListAdapter<LegalFormat, LegalFormatsViewHolder>(
    AppDiffUtil<LegalFormat>(
        itemSame = { old, new -> old == new },
        contentSame = { old, new -> old == new }
    )
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LegalFormatsViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.view_common_section_list_type_item, parent, false)
        return LegalFormatsViewHolder(view)
    }

    override fun onBindViewHolder(holder: LegalFormatsViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}