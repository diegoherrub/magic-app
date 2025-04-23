package pol.rubiano.magicapp.features.decks.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import pol.rubiano.magicapp.app.presentation.AppDiffUtil
import pol.rubiano.magicapp.features.decks.domain.models.Deck

class DecksAdapter(
    private val onDeckClickToDetails: (Deck) -> Unit,
) : ListAdapter<Deck, DecksViewHolder>(
    AppDiffUtil<Deck>(
        itemSame = { old, new -> old.id == new.id},
        contentSame = { old, new -> old == new }
    )
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DecksViewHolder {
        return DecksViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DecksViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onDeckClickToDetails)
    }
}