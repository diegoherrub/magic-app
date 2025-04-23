package pol.rubiano.magicapp.features.magic.presentation.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import pol.rubiano.magicapp.app.common.utils.mapManaSymbols
import pol.rubiano.magicapp.databinding.ViewCommonSectionListTypeItemBinding
import pol.rubiano.magicapp.features.magic.domain.modules.Keyword

class KeywordsViewHolder(
    val view: View
) : RecyclerView.ViewHolder(view) {

    private lateinit var binding: ViewCommonSectionListTypeItemBinding

    fun bind(keyword: Keyword) {
        binding = ViewCommonSectionListTypeItemBinding.bind(view)

        binding.apply {
            keyword.icon?.let { viewCommonSectionListTypeIcon.setImageResource(it) }
            viewCommonSectionListTypeTitle.text = keyword.term
            viewCommonSectionListTypeContent.text = mapManaSymbols(viewCommonSectionListTypeContent.context, keyword.information)
        }
    }
}