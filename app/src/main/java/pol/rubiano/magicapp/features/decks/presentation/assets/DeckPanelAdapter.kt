package pol.rubiano.magicapp.features.decks.presentation.assets

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.common.extensions.loadUrl
import pol.rubiano.magicapp.app.presentation.AppDiffUtil
import pol.rubiano.magicapp.features.decks.domain.models.DeckConfigItem

class DeckPanelAdapter : ListAdapter<DeckConfigItem, RecyclerView.ViewHolder>(
    AppDiffUtil<DeckConfigItem>(
        itemSame = { old, new -> old == new },
        contentSame = { old, new -> old == new }
    )
) {
    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_GROUP = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DeckConfigItem.Header -> VIEW_TYPE_HEADER
            is DeckConfigItem.CardGroup -> VIEW_TYPE_GROUP
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.separator_header, parent, false)
                HeaderViewHolder(view)
            }
            VIEW_TYPE_GROUP -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.deck_item_cards_row, parent, false)
                CardGroupViewHolder(view)
            }
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is DeckConfigItem.Header -> (holder as HeaderViewHolder).bind(item)
            is DeckConfigItem.CardGroup -> (holder as CardGroupViewHolder).bind(item)
        }
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: DeckConfigItem.Header) {
            itemView.findViewById<TextView>(R.id.separator_header).text = item.title
        }
    }

    class CardGroupViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val container = itemView.findViewById<ConstraintLayout>(R.id.cardRowContainer)
        private val boardIndicator = itemView.findViewById<TextView>(R.id.boardIndicator)

        fun bind(item: DeckConfigItem.CardGroup) {
            container.removeAllViews()

            boardIndicator.text = buildString {
                append("Main: ${item.mainBoardCopies}")
                if (item.sideBoardCopies > 0) append(" | Side: ${item.sideBoardCopies}")
                if (item.mayBeBoardCopies > 0) append(" | Maybe: ${item.mayBeBoardCopies}")
            }

            item.cards.groupBy { it.first.id }.forEach { (_, cardPairs) ->
                val (card, cardInDeck) = cardPairs.first()
                val rowView = LayoutInflater.from(container.context)
                    .inflate(R.layout.deck_item_cards_row, container, false)

                setupBoard(rowView, R.id.mainCopy1, R.id.mainCopy2, R.id.mainCopy3, R.id.mainCopy4,
                    cardInDeck.mainBoardCopies, card.imageSmall)
                setupBoard(rowView, R.id.sideCopy1, R.id.sideCopy2, R.id.sideCopy3, R.id.sideCopy4,
                    cardInDeck.sideBoardCopies, card.imageSmall)
                setupBoard(rowView, R.id.maybeCopy1, R.id.maybeCopy2, R.id.maybeCopy3, R.id.maybeCopy4,
                    cardInDeck.mayBeBoardCopies, card.imageSmall)

                container.addView(rowView)
            }
        }

        private fun setupBoard(
            rowView: View,
            id1: Int, id2: Int, id3: Int, id4: Int,
            copies: Int,
            imageUrl: String?
        ) {
            val imageViews = listOf(
                rowView.findViewById<ImageView>(id1),
                rowView.findViewById<ImageView>(id2),
                rowView.findViewById<ImageView>(id3),
                rowView.findViewById<ImageView>(id4)
            )

            imageViews.forEachIndexed { index, imageView ->
                imageView.visibility = if (index < copies) View.VISIBLE else View.INVISIBLE
            }

            if (copies > 0 && imageUrl != null) {
                imageViews.first().loadUrl(imageUrl)
            }
        }
    }
}