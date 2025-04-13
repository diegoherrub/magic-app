package pol.rubiano.magicapp.features.collections.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import pol.rubiano.magicapp.databinding.CollectionsItemBinding
import pol.rubiano.magicapp.features.collections.domain.Collection

class CollectionsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private lateinit var binding: CollectionsItemBinding

    fun bind(collection: Collection) {
        binding = CollectionsItemBinding.bind(view)
        binding.apply {
            collectionsItemName.text = collection.name
        }
    }
}