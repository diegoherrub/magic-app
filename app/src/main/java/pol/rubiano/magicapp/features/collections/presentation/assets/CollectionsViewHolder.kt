package pol.rubiano.magicapp.features.collections.presentation.assets

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.databinding.CollectionPanelBinding
import pol.rubiano.magicapp.databinding.CollectionsListItemBinding
import pol.rubiano.magicapp.features.collections.domain.CardInCollection
import pol.rubiano.magicapp.features.collections.domain.Collection

class CollectionsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private lateinit var collectionsListItemBinding: CollectionsListItemBinding
    private lateinit var collectionPanelBinding: CollectionPanelBinding

    fun bindCollectionsList(
        collection: Collection,
        onCollectionNameClick: (String) -> Unit
        ) {
        collectionsListItemBinding = CollectionsListItemBinding.bind(view)
        collectionsListItemBinding.apply {
            collectionsItemName.text = collection.name
            root.setOnClickListener{
                onCollectionNameClick(collection.name)
            }
        }
    }

    fun bindCollectionPanelCards(
        cardInCollection: CardInCollection
    ) {
        collectionPanelBinding = CollectionPanelBinding.bind(view)
        collectionPanelBinding.apply {
            val textCardCopies = view.findViewById<TextView>(R.id.textCardCopies)
            textCardCopies.text = cardInCollection.copies.toString()
        }
    }
}