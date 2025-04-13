package pol.rubiano.magicapp.features.collections.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import pol.rubiano.magicapp.databinding.CollectionsListItemBinding
import pol.rubiano.magicapp.features.collections.domain.Collection

class CollectionsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private lateinit var binding: CollectionsListItemBinding

    fun bind(collection: Collection) {
        binding = CollectionsListItemBinding.bind(view)
        binding.apply {
            collectionsItemName.text = collection.name
        }
    }
}