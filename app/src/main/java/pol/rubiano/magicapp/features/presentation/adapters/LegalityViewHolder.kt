package pol.rubiano.magicapp.features.presentation.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import pol.rubiano.magicapp.databinding.LegalitiesItemViewBinding
import pol.rubiano.magicapp.features.domain.LegalityTerm

class LegalityViewHolder(
    val view: View
) : RecyclerView.ViewHolder(view) {

    private lateinit var binding: LegalitiesItemViewBinding

    fun bind (term: LegalityTerm) {
        binding = LegalitiesItemViewBinding.bind(view)

        binding.apply {

        }
    }
}