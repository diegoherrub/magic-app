package pol.rubiano.magicapp.features.deckdetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.domain.models.CardCategory
import pol.rubiano.magicapp.app.presentation.AppDiffUtil
import pol.rubiano.magicapp.features.domain.models.DeckConfigItem

class DeckDetailsAdapter(
    private val onAddCardClick: (CardCategory) -> Unit = {}
) : ListAdapter<DeckConfigItem, RecyclerView.ViewHolder>(
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
                    .inflate(R.layout.deck_item_header, parent, false)
                HeaderViewHolder(view)
            }

            VIEW_TYPE_GROUP -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.deck_item_cards_row, parent, false)
                CardGroupViewHolder(view, onAddCardClick)
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
            itemView.findViewById<TextView>(R.id.headerTitle).text = item.title
        }
    }

    class CardGroupViewHolder(
        view: View,
        private val onAddCardClick: (CardCategory) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val container = itemView.findViewById<LinearLayout>(R.id.cardContainer)

        fun bind(item: DeckConfigItem.CardGroup) {
            container.removeAllViews()

            val context = container.context

            if (item.cards.isEmpty()) {
                val addButton = ImageView(context).apply {
                    setImageResource(android.R.drawable.ic_input_add)
                    layoutParams = LinearLayout.LayoutParams(0, 100.dp(context), 1f).apply {
                        marginStart = 8.dp(context)
                        marginEnd = 8.dp(context)
                    }
                    setOnClickListener {
                        onAddCardClick(item.category)
                    }
                }
                container.addView(addButton)
            } else {
                item.cards.forEach { card ->
                    val cardView = LayoutInflater.from(context)
                        .inflate(R.layout.deck_item_card, container, false)
//                    cardView.findViewById<TextView>(R.id.cardName).text = card.name
                    container.addView(cardView)
                }
            }
        }

        private fun Int.dp(context: Context): Int =
            (this * context.resources.displayMetrics.density).toInt()
    }
}
