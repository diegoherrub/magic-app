package pol.rubiano.magicapp.features.presentation.adapters.diffutils

import androidx.recyclerview.widget.DiffUtil
import pol.rubiano.magicapp.features.domain.models.Deck

class DeckDiffUtil : DiffUtil.ItemCallback<Deck>() {
    override fun areItemsTheSame(oldItem: Deck, newItem: Deck) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Deck, newItem: Deck) = oldItem == newItem
}