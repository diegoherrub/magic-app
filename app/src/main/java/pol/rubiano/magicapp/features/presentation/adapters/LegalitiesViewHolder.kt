package pol.rubiano.magicapp.features.presentation.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import pol.rubiano.magicapp.databinding.ViewCommonSectionListTypeItemBinding
import pol.rubiano.magicapp.features.domain.Legality

class LegalitiesViewHolder(
    val view: View
) : RecyclerView.ViewHolder(view) {

    private lateinit var binding: ViewCommonSectionListTypeItemBinding

    fun bind(legality: Legality) {
        binding = ViewCommonSectionListTypeItemBinding.bind(view)

        binding.apply {
            viewCommonSectionListTypeTitle.text = legality.term
            viewCommonSectionListTypeContent.text = legality.information
        }
    }
}