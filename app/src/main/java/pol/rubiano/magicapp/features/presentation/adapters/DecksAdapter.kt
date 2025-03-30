package pol.rubiano.magicapp.features.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pol.rubiano.magicapp.databinding.DeckFragmentItemBinding
import pol.rubiano.magicapp.features.domain.entities.Deck
import pol.rubiano.magicapp.features.presentation.adapters.viewholders.DecksViewHolder

class DecksAdapter(
    private var decks: List<Deck> = emptyList(),
    private val onItemClicked: (Deck) -> Unit
) : RecyclerView.Adapter<DecksViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DecksViewHolder {
        val binding = DeckFragmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DecksViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: DecksViewHolder, position: Int) {
        val deck = decks[position]
        holder.bind(deck)
    }

    override fun getItemCount(): Int = decks.size

        fun updateDecks(newDecks: List<Deck>) {
        decks = newDecks
        notifyDataSetChanged()
    }

}
