package pol.rubiano.magicapp.features.decks

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import pol.rubiano.magicapp.features.domain.models.Deck

class DecksAdapter(
    private val onDeckClickToDetails: (Deck) -> Unit,
) : ListAdapter<Deck, DecksViewHolder>(DeckDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DecksViewHolder {
        return DecksViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DecksViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onDeckClickToDetails)
    }
}