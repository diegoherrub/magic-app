package pol.rubiano.magicapp.features.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import pol.rubiano.magicapp.features.domain.Keyword

class KeywordsDiffUtil : DiffUtil.ItemCallback<Keyword>() {
    override fun areItemsTheSame(oldItem: Keyword, newItem: Keyword) = oldItem.term == newItem.term
    override fun areContentsTheSame(oldItem: Keyword, newItem: Keyword) = oldItem == newItem    
}