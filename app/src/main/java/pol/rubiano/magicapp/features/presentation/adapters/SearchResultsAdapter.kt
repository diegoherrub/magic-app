package pol.rubiano.magicapp.features.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.domain.models.Card
import pol.rubiano.magicapp.app.presentation.AppDiffUtil
import pol.rubiano.magicapp.features.presentation.adapters.viewholders.SearchResultsViewHolder

class SearchResultsAdapter(
    private val onCardClicked: (Card) -> Unit
) : ListAdapter<Card, SearchResultsViewHolder>(
    AppDiffUtil<Card>(
        itemSame = {old, new -> old.id == new.id},
        contentSame = {old, new -> old == new}
    )
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.search_results_item, parent, false)
        return SearchResultsViewHolder(view, onCardClicked)
    }

    override fun onBindViewHolder(holder: SearchResultsViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}