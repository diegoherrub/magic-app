package pol.rubiano.magicapp.features.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.domain.models.Card
import pol.rubiano.magicapp.features.presentation.adapters.diffutils.SearchResultsDiffUtil
import pol.rubiano.magicapp.features.presentation.adapters.viewholders.SearchResultsViewHolder

class SearchResultsAdapter(
    private val onCardClicked: (Card) -> Unit
) : ListAdapter<Card, SearchResultsViewHolder>(SearchResultsDiffUtil()) {

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