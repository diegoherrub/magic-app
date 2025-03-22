package pol.rubiano.magicapp.features.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import pol.rubiano.magicapp.app.domain.Card

class SearchResultsDiffUtil  : DiffUtil.ItemCallback<Card>() {
    override fun areItemsTheSame(oldItem: Card, newItem: Card) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Card, newItem: Card) = oldItem == newItem
}