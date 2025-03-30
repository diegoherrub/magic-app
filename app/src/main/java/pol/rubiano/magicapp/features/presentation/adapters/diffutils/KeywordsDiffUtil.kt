package pol.rubiano.magicapp.features.presentation.adapters.diffutils

import androidx.recyclerview.widget.DiffUtil
import pol.rubiano.magicapp.features.domain.entities.Keyword

class KeywordsDiffUtil : DiffUtil.ItemCallback<Keyword>() {
    override fun areItemsTheSame(oldItem: Keyword, newItem: Keyword) = oldItem.term == newItem.term
    override fun areContentsTheSame(oldItem: Keyword, newItem: Keyword) = oldItem == newItem
}