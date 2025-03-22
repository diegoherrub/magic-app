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
import pol.rubiano.magicapp.features.domain.GlossaryItem
import pol.rubiano.magicapp.features.domain.GlossaryTerm

class GlossaryAdapter(
    private val originalItems: List<GlossaryItem>
) : RecyclerView.Adapter<GlossaryAdapter.EntryViewHolder>(), Filterable {

    // Inicialmente filtramos solo las entradas
    private var filteredItems: List<GlossaryItem> = originalItems.filter { it is GlossaryItem.Entry }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.glossary_entry_view, parent, false)
        return EntryViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        // Siempre tendremos únicamente entradas, por lo que hacemos el cast directamente
        val item = filteredItems[position] as GlossaryItem.Entry
        holder.bind(item.glossaryTerm)
    }

    override fun getItemCount(): Int = filteredItems.size

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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase() ?: ""
                val result: List<GlossaryItem> = if (query.isEmpty()) {
                    originalItems.filter { it is GlossaryItem.Entry }
                } else {
                    originalItems.filter { item ->
                        item is GlossaryItem.Entry && item.glossaryTerm.term.lowercase().contains(query)
                    }
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
