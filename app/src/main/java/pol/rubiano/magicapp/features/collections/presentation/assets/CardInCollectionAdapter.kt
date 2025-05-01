package pol.rubiano.magicapp.features.collections.presentation.assets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.presentation.AppDiffUtil
import pol.rubiano.magicapp.features.collections.domain.models.CardInCollection

class CardsInCollectionAdapter : ListAdapter<CardInCollection, CardInCollectionViewHolder>(
    AppDiffUtil<CardInCollection>(
        itemSame = { old, new -> old.cardId == new.cardId },
        contentSame = { old, new -> old == new }
    )
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardInCollectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.small_card_in_collection_layout, parent, false)
        return CardInCollectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardInCollectionViewHolder, position: Int) {
        holder.bindCollectionPanelCards(currentList[position])
    }
}