package pol.rubiano.magicapp.features.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.ListAdapter
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.features.domain.entities.Keyword
import pol.rubiano.magicapp.features.presentation.adapters.diffutils.KeywordsDiffUtil
import pol.rubiano.magicapp.features.presentation.adapters.viewholders.KeywordsViewHolder

class KeywordsAdapter() : ListAdapter<Keyword, KeywordsViewHolder>(KeywordsDiffUtil()), Filterable {

    private var originalKeywords: List<Keyword> = emptyList()
    private var filteredKeywords: List<Keyword> = emptyList()

//    override fun submitList(list: List<Keyword>?) {
//        super.submitList(list)
//        list?.let {
//            originalKeywords = it
//            filteredKeywords = it
//        }
//    }

//    override fun submitList(list: List<Keyword>?) {
//        super.submitList(list?.toList())
//        list?.let {
//            originalKeywords = it
//            filteredKeywords = it
//        }
//    }

    fun setKeywords(keywords: List<Keyword>) {
        originalKeywords = keywords
        filteredKeywords = keywords
        submitList(keywords)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordsViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.view_common_section_list_type_item, parent, false)
        return KeywordsViewHolder(view)
    }

    //    override fun onBindViewHolder(holder: KeywordsViewHolder, position: Int) {
//        holder.bind(currentList[position])
//    }
    override fun onBindViewHolder(holder: KeywordsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase() ?: ""
                val result: List<Keyword> = if (query.isEmpty()) {
                    originalKeywords
                } else {
                    originalKeywords.filter { it.term.lowercase().contains(query) }
                }
                return FilterResults().apply { values = result }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredKeywords = results?.values as? List<Keyword> ?: emptyList()
                submitList(filteredKeywords)
            }
        }
    }
}
