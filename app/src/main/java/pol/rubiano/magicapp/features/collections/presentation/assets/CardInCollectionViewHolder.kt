package pol.rubiano.magicapp.features.collections.presentation.assets

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.common.extensions.loadImage
import pol.rubiano.magicapp.databinding.SmallCardInCollectionLayoutBinding
import pol.rubiano.magicapp.features.collections.domain.CardInCollection

class CardInCollectionViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private lateinit var binding: SmallCardInCollectionLayoutBinding

    fun bindCollectionPanelCards(
        cardInCollection: CardInCollection
    ) {
        binding = SmallCardInCollectionLayoutBinding.bind(view)
        binding.apply {
            val textCardCopies = view.findViewById<TextView>(R.id.textCardCopies)
            textCardCopies.text = cardInCollection.copies.toString()
            binding.smallCardImage.loadImage(view.context, cardInCollection.cardId)
        }
    }
}