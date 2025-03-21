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
import pol.rubiano.magicapp.features.domain.LegalityTerm

class LegalityAdapter(
    private val originalItems: List<LegalityTerm>
) : RecyclerView.Adapter<LegalityAdapter.EntryViewHolder>(), Filterable {

    private var filteredItems: List<LegalityTerm> = originalItems

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.legality_item_entry_view, parent, false)
        return EntryViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val term = filteredItems[position]
        holder.bind(term)
    }

    override fun getItemCount(): Int = filteredItems.size

    class EntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTerm: TextView = itemView.findViewById(R.id.app_legalities_item_term)
        private val tvDefinition: TextView =
            itemView.findViewById(R.id.app_legalities_item_description)
        private val ivLegalityImage: ImageView =
            itemView.findViewById(R.id.app_legalities_item_image)

        fun bind(term: LegalityTerm) {
            tvTerm.text = term.term
            tvDefinition.text = term.definition
            if (term.imageResId != null && term.imageResId != 0) {
                ivLegalityImage.setImageResource(term.imageResId)
                ivLegalityImage.visibility = View.VISIBLE
            } else {
                ivLegalityImage.visibility = View.GONE
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase() ?: ""
                val result: List<LegalityTerm> = if (query.isEmpty()) {
                    originalItems
                } else {
                    originalItems.filter { term ->
                        term.term.lowercase().contains(query)
                    }
                }
                return FilterResults().apply { values = result }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredItems = results?.values as? List<LegalityTerm> ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }
}
