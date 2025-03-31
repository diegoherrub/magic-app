package pol.rubiano.magicapp.features.presentation.adapters.diffutils

import androidx.recyclerview.widget.DiffUtil
import pol.rubiano.magicapp.features.domain.models.DeckConfigItem

class DeckConfigDiffUtil : DiffUtil.ItemCallback<DeckConfigItem>() {
    override fun areItemsTheSame(oldItem: DeckConfigItem, newItem: DeckConfigItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DeckConfigItem, newItem: DeckConfigItem): Boolean {
        return oldItem == newItem
    }
}
