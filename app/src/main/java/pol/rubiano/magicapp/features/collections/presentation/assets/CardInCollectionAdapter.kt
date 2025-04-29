package pol.rubiano.magicapp.features.collections.presentation.assets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.presentation.AppDiffUtil
import pol.rubiano.magicapp.features.collections.domain.CardInCollection

class CardsInCollectionAdapter : ListAdapter<CardInCollection, CollectionsViewHolder>(
    AppDiffUtil<CardInCollection>(
        itemSame = { old, new -> old.cardId == new.cardId },
        contentSame = { old, new -> old == new }
    )
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.small_card_in_collection_layout, parent, false)
        return CollectionsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CollectionsViewHolder, position: Int) {
        holder.bindCollectionPanelCards(currentList[position])
    }
}