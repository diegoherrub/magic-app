package pol.rubiano.magicapp.features.collections.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import pol.rubiano.magicapp.app.presentation.AppDiffUtil
import pol.rubiano.magicapp.features.collections.domain.Collection

import pol.rubiano.magicapp.R

class CollectionsAdapter : ListAdapter<Collection, CollectionsViewHolder>(
    AppDiffUtil<Collection>(
        itemSame = { old, new -> old.name == new.name },
        contentSame = { old, new -> old == new }
    )
) {

    companion object {
        private const val VIEW_TYPE_COLLECTION = 0
        private const val VIEW_TYPE_NEW_COLLECTION = 1
    }

    // El total de items es el número de colecciones + 1 (para el footer)
    override fun getItemCount(): Int = currentList.size + 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.collections_item, parent, false)
        return CollectionsViewHolder(view)
    }


    override fun onBindViewHolder(holder: CollectionsViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}
