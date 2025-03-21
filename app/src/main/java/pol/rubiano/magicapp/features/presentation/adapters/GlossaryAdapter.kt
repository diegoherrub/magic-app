package pol.rubiano.magicapp.features.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.features.data.local.groupGlossaryTerms
import pol.rubiano.magicapp.features.domain.GlossaryItem
import pol.rubiano.magicapp.features.domain.GlossaryTerm

class GlossaryAdapter(
    private val originalItems: List<GlossaryItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private var filteredItems: List<GlossaryItem> = originalItems

    companion object {
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_ENTRY = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (filteredItems[position]) {
            is GlossaryItem.Header -> VIEW_TYPE_HEADER
            is GlossaryItem.Entry -> VIEW_TYPE_ENTRY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.glossary_item_header_view, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.glossary_item_entry_view, parent, false)
            EntryViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = filteredItems[position]) {
            is GlossaryItem.Header -> (holder as HeaderViewHolder).bind(item) { pos ->
                val headerPos = findHeaderPositionForItemInOriginalList(pos)
                if (headerPos != -1) {
                    val header = originalItems[headerPos] as GlossaryItem.Header
                    header.isExpanded = !header.isExpanded
                    updateList()
                }
            }
            is GlossaryItem.Entry -> (holder as EntryViewHolder).bind(item.glossaryTerm)
        }
    }

    override fun getItemCount(): Int = filteredItems.size

    fun getItem(position: Int): GlossaryItem = filteredItems[position]

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvHeader: TextView = itemView.findViewById(R.id.title_sticky_header)
        fun bind(item: GlossaryItem.Header, toggle: (Int) -> Unit) {
            tvHeader.text = item.category
            // Al pulsar en la cabecera, se activa el toggle
            itemView.setOnClickListener { toggle(adapterPosition) }
        }
    }

    class EntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTerm: TextView = itemView.findViewById(R.id.tvTerm)
        private val tvDefinition: TextView = itemView.findViewById(R.id.tvDefinition)
        private val ivGlossaryImage: ImageView = itemView.findViewById(R.id.ivGlossaryImage)
        fun bind(term: GlossaryTerm) {
            tvTerm.text = term.term
            tvDefinition.text = term.definition
            if (term.imageResId != null && term.imageResId != 0) {
                ivGlossaryImage.setImageResource(term.imageResId)
                ivGlossaryImage.visibility = View.VISIBLE
            } else {
                ivGlossaryImage.visibility = View.GONE
            }
        }
    }

    private fun updateList() {
        val newList = mutableListOf<GlossaryItem>()
        var currentHeader: GlossaryItem.Header? = null
        for (item in originalItems) {
            when (item) {
                is GlossaryItem.Header -> {
                    newList.add(item)
                    currentHeader = item
                }
                is GlossaryItem.Entry -> {
                    if (currentHeader?.isExpanded == true) {
                        newList.add(item)
                    }
                }
            }
        }
        filteredItems = newList
        notifyDataSetChanged()
    }

    // Mapea la posición visible de la cabecera a la posición en originalItems
    private fun findHeaderPositionForItemInOriginalList(visiblePos: Int): Int {
        var visibleHeaderCount = 0
        for (i in originalItems.indices) {
            if (originalItems[i] is GlossaryItem.Header) {
                if (visibleHeaderCount == visiblePos) {
                    return i
                }
                visibleHeaderCount++
            }
        }
        return -1
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase() ?: ""
                val result: List<GlossaryItem> = if (query.isEmpty()) {
                    originalItems
                } else {
                    // Filtramos solo los elementos de tipo Entry y luego los reagrupamos
                    val filteredEntries = originalItems.filter { item ->
                        when (item) {
                            is GlossaryItem.Entry -> item.glossaryTerm.term.lowercase()
                                .contains(query)

                            is GlossaryItem.Header -> false
                        }
                    }.map { (it as GlossaryItem.Entry).glossaryTerm }
                    groupGlossaryTerms(filteredEntries)
                }
                return FilterResults().apply { values = result }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredItems = results?.values as? List<GlossaryItem> ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }
}
