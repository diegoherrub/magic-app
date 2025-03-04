package pol.rubiano.magicapp.app.presentation.legalities

import androidx.recyclerview.widget.DiffUtil
import pol.rubiano.magicapp.app.domain.entities.LegalityItem

class LegalitiesDiffUtil : DiffUtil.ItemCallback<LegalityItem>() {
    override fun areItemsTheSame(oldItem: LegalityItem, newItem: LegalityItem): Boolean {
        return oldItem.format == newItem.format
    }

    override fun areContentsTheSame(oldItem: LegalityItem, newItem: LegalityItem): Boolean {
        return oldItem == newItem
    }
}