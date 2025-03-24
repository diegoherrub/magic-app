package pol.rubiano.magicapp.features.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import pol.rubiano.magicapp.features.domain.Legality

class LegalitiesDiffUtil : DiffUtil.ItemCallback<Legality>() {
    override fun areItemsTheSame(oldItem: Legality, newItem: Legality) = oldItem.term == newItem.term
    override fun areContentsTheSame(oldItem: Legality, newItem: Legality) = oldItem == newItem
}