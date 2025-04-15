package pol.rubiano.magicapp.features.collections.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import pol.rubiano.magicapp.app.presentation.AppDiffUtil
import pol.rubiano.magicapp.features.collections.domain.Collection

import pol.rubiano.magicapp.R

class CollectionsAdapter(
    private val onCollectionNameClick: (String) -> Unit
) : ListAdapter<Collection, CollectionsViewHolder>(
    AppDiffUtil<Collection>(
        itemSame = { old, new -> old.name == new.name },
        contentSame = { old, new -> old == new }
    )
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.collections_list_item, parent, false)
        return CollectionsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CollectionsViewHolder, position: Int) {
        holder.bindCollectionsList(currentList[position], onCollectionNameClick)
    }
}
