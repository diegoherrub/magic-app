package pol.rubiano.magicapp.features.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import pol.rubiano.magicapp.features.domain.models.Deck
import pol.rubiano.magicapp.features.presentation.adapters.diffutils.DeckDiffUtil
import pol.rubiano.magicapp.features.presentation.adapters.viewholders.DecksViewHolder

class DecksAdapter(
    private val onDeckClickToConfig: (Deck) -> Unit,
   // private val onDeckClickToEdit: (Deck) -> Unit
) : ListAdapter<Deck, DecksViewHolder>(DeckDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DecksViewHolder {
        return DecksViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DecksViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onDeckClickToConfig)
    }
}