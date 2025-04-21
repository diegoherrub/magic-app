package pol.rubiano.magicapp.features.decks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pol.rubiano.magicapp.app.cards.data.mapManaSymbols
import pol.rubiano.magicapp.databinding.DeckItemBinding
import pol.rubiano.magicapp.features.domain.models.Deck

class DecksViewHolder(
    private val binding: DeckItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(deck: Deck, onDeckClick: (Deck) -> Unit) {
        binding.deckItemName.text = deck.name
        binding.deckDescription.text = deck.description
        binding.deckColors.text = mapManaSymbols(
            binding.root.context,
            deck.colors.joinToString(" ") { "{$it}" }
        )
        binding.root.setOnClickListener {
            onDeckClick(deck)
        }
    }

    companion object {
        fun from(parent: ViewGroup): DecksViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = DeckItemBinding.inflate(layoutInflater, parent, false)
            return DecksViewHolder(binding)
        }
    }
}
