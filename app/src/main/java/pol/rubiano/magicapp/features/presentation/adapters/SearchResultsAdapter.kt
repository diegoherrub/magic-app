package pol.rubiano.magicapp.features.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.domain.Card

class SearchResultsAdapter() : ListAdapter<Card, SearchResultsViewHolder>(SearchResultsDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.search_results_item, parent, false)
        return SearchResultsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchResultsViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}