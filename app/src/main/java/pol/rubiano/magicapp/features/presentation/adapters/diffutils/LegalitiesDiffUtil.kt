package pol.rubiano.magicapp.features.presentation.adapters.diffutils

import androidx.recyclerview.widget.DiffUtil
import pol.rubiano.magicapp.features.domain.entities.Legality

class LegalitiesDiffUtil : DiffUtil.ItemCallback<Legality>() {
    override fun areItemsTheSame(oldItem: Legality, newItem: Legality) = oldItem.term == newItem.term
    override fun areContentsTheSame(oldItem: Legality, newItem: Legality) = oldItem == newItem
}