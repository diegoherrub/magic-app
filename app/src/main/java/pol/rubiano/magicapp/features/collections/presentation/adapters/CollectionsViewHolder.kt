package pol.rubiano.magicapp.features.collections.presentation.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
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

    fun bindCollectionPanel(
        cardInCollection: CardInCollection
    ) {
        collectionPanelBinding = CollectionPanelBinding.bind(view)
//        collectionPanelBinding.apply {
//            collectionName.text = cardInCollection.cardId
//            collectionName.text = cardInCollection.copies.toString()
//        }
    }
}