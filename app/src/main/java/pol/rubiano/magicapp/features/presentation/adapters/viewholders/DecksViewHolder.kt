package pol.rubiano.magicapp.features.presentation.adapters.viewholders

import androidx.recyclerview.widget.RecyclerView
import pol.rubiano.magicapp.databinding.DeckFragmentItemBinding
import pol.rubiano.magicapp.features.domain.entities.Deck

class DecksViewHolder(
    private val binding: DeckFragmentItemBinding,
    private val onItemClicked: (Deck) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(deck: Deck) {
        binding.deckItemName.text = deck.name
        binding.root.setOnClickListener { onItemClicked(deck) }
    }
}
