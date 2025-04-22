package pol.rubiano.magicapp.features.search.presentation.assets

import android.view.View
import androidx.core.text.buildSpannedString
import androidx.recyclerview.widget.RecyclerView
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.common.extensions.loadUrl
import pol.rubiano.magicapp.app.cards.data.mapManaSymbols
import pol.rubiano.magicapp.databinding.SearchResultsItemBinding
import pol.rubiano.magicapp.features.cards.domain.models.Card

class SearchResultsViewHolder(
    val view: View,
    private val onCardClicked: (Card) -> Unit
) : RecyclerView.ViewHolder(view) {
    private lateinit var binding: SearchResultsItemBinding
    private var isTwoFaced: Boolean = false

    fun bind(card: Card) {
        view.setOnClickListener { onCardClicked(card) }

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
                    append(card.rarity?.replaceFirstChar { char -> char.uppercaseChar() })
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
                        append(card.rarity?.replaceFirstChar { char -> char.uppercaseChar() })
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