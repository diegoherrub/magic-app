package pol.rubiano.magicapp.features.magic.presentation.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import pol.rubiano.magicapp.databinding.ViewCommonSectionListTypeItemBinding
import pol.rubiano.magicapp.features.magic.domain.modules.LegalFormat

class LegalFormatsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private lateinit var binding: ViewCommonSectionListTypeItemBinding

    fun bind(legalFormat: LegalFormat) {
        binding = ViewCommonSectionListTypeItemBinding.bind(view)

        binding.apply {
            viewCommonSectionListTypeTitle.text = legalFormat.term
            viewCommonSectionListTypeContent.text = legalFormat.information
        }
    }
}