package pol.rubiano.magicapp.features.decks.deckdetails

import android.content.Context
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
import pol.rubiano.magicapp.features.domain.models.DeckConfigItem

class DeckDetailsAdapter(
//    private val onAddCardClick: (CardCategory) -> Unit = {}
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
                    .inflate(R.layout.separator_header, parent, false)
                HeaderViewHolder(view)
            }

            VIEW_TYPE_GROUP -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.deck_item_cards_row, parent, false)
//                CardGroupViewHolder(view, onAddCardClick)
                CardGroupViewHolder(view)
            }

            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is DeckConfigItem.Header -> {
                Log.d("@pol", "Binding header: ${item.title}")
                (holder as HeaderViewHolder).bind(item)
            }

            is DeckConfigItem.CardGroup -> (holder as CardGroupViewHolder).bind(item)
        }
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: DeckConfigItem.Header) {
            itemView.findViewById<TextView>(R.id.separator_header).text = item.title
        }
    }

    class CardGroupViewHolder(
        view: View,
//        private val onAddCardClick: (CardCategory) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val container = itemView.findViewById<ConstraintLayout>(R.id.cardRowContainer)

        fun bind(item: DeckConfigItem.CardGroup) {
            container.removeAllViews()
            val context = container.context

//            if (item.cards.isEmpty()) {
//                val addButton = ImageView(context).apply {
////                    setImageResource(android.R.drawable.ic_input_add)
////                    layoutParams = LinearLayout.LayoutParams(
////                        LinearLayout.LayoutParams.MATCH_PARENT,
////                        100.dp(context)
////                    ).apply {
////                        marginStart = 8.dp(context)
////                        marginEnd = 8.dp(context)
////                    }
////                    setOnClickListener {
////                        onAddCardClick(item.category)
////                    }
//                }
//                container.addView(addButton)
//            } else {
                val groupedCards = item.cards.groupBy { it?.id ?: "" }
                for ((_, cardList) in groupedCards) {
                    val copiesCount = cardList.size

                    val rowView = LayoutInflater.from(context)
                        .inflate(R.layout.deck_item_cards_row, container, false)

                    // val copy1 = rowView.findViewById<ImageView>(R.id.cardFirstCopy)
                    val copy1 = rowView.findViewById<ImageView>(R.id.cardFirstCopy)
                    val copy2 = rowView.findViewById<ImageView>(R.id.cardSecondCopy)
                    val copy3 = rowView.findViewById<ImageView>(R.id.cardThirdCopy)
                    val copy4 = rowView.findViewById<ImageView>(R.id.cardForthCopy)

                    val imageDrawable: Unit
                    val card = cardList.firstOrNull()
                    card?.imageSmall?.let { url ->
                        // Cargar la imagen en copy1
                        copy1.loadUrl(url)


                        // Usar el mismo Drawable para las demás copias
                        copy1.post {
                            val drawable = copy1.drawable
                            copy2.setImageDrawable(copy1.drawable)
                            copy3.setImageDrawable(copy1.drawable)
                            copy4.setImageDrawable(copy1.drawable)
                        }
                    }

                    // Show or hide each copy based on copiesCount (1..4)
                    copy1.visibility = if (copiesCount >= 1) View.VISIBLE else View.INVISIBLE
                    copy2.visibility = if (copiesCount >= 2) View.VISIBLE else View.INVISIBLE
                    copy3.visibility = if (copiesCount >= 3) View.VISIBLE else View.INVISIBLE
                    copy4.visibility = if (copiesCount >= 4) View.VISIBLE else View.INVISIBLE

                    container.addView(rowView)
                //}
            }
        }
    }
}
private fun Int.dp(context: Context): Int =
    (this * context.resources.displayMetrics.density).toInt()