package pol.rubiano.magicapp.features.presentation.adapters

import android.view.View
import androidx.core.text.buildSpannedString
import androidx.recyclerview.widget.RecyclerView
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.common.extensions.loadUrl
import pol.rubiano.magicapp.app.data.mapManaSymbols
import pol.rubiano.magicapp.databinding.SearchResultsItemBinding
import pol.rubiano.magicapp.app.domain.Card

class SearchResultsViewHolder(
    val view: View
) : RecyclerView.ViewHolder(view) {
    private lateinit var binding: SearchResultsItemBinding
    private var isTwoFaced: Boolean = false

    fun bind(card: Card) {
        isTwoFaced = (card.frontFace != null && card.backFace != null)

        binding = SearchResultsItemBinding.bind(view)
        binding.apply {
            searchResultsItemCardName.text = card.name
            card.imageSmall?.let { searchResultsItemImage.loadUrl(it) } ?: R.drawable.card_back
            if (!isTwoFaced) {
                if (card.imageSmall != null) {
                    searchResultsItemImage.loadUrl(card.imageSmall)
                } else {
                    card.frontFace?.faceImageSmall?.let {
                        searchResultsItemImage.loadUrl(it)
                    } ?: searchResultsItemImage.setImageResource(R.drawable.card_back)
                }
                searchResultsItemCardTypeLine.text = card.typeLine
                searchResultsItemCardRarityManaCost.text = buildSpannedString {
                    append(card.rarity)
                    card.manaCost?.let {
                        append(" - ")
                        append(mapManaSymbols(searchResultsItemCardRarityManaCost.context, it))
                    }
                }
                searchResultsItemCardOracle.text = card.oracleText?.let {
                    mapManaSymbols(searchResultsItemCardOracle.context, it)
                } ?: ""
            } else {
                card.frontFace?.let { face ->
                    face.faceImageSmall?.let { searchResultsItemImage.loadUrl(it) }
                        ?: R.drawable.card_back
                    searchResultsItemCardTypeLine.text = face.faceTypeLine
                    searchResultsItemCardRarityManaCost.text = buildSpannedString {
                        append(card.rarity)
                        face.faceManaCost?.let {
                            append(" - ")
                            append(mapManaSymbols(searchResultsItemCardRarityManaCost.context, it))
                        }
                    }
                    searchResultsItemCardOracle.text = face.faceOracleText?.let {
                        mapManaSymbols(searchResultsItemCardOracle.context, it)
                    } ?: ""
                }
            }
        }
    }
}