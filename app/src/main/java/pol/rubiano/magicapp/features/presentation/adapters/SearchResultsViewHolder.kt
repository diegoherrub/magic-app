package pol.rubiano.magicapp.features.presentation.adapters

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import pol.rubiano.magicapp.app.common.extensions.loadUrl
import pol.rubiano.magicapp.databinding.SearchResultsItemBinding
import pol.rubiano.magicapp.app.domain.Card

class SearchResultsViewHolder (
    val view: View
) : RecyclerView.ViewHolder(view)
{
    private lateinit var binding: SearchResultsItemBinding

    fun bind(card: Card) {
        binding = SearchResultsItemBinding.bind(view)

        Log.d("@POL", " card = ${card}")
        binding.apply {

            card.imageSmall?.let { searchResultsItemImage.loadUrl(it) }
            Log.d("@POL", "se ha cargado la imagen")
            searchResultsItemCardName.text = card.name
        }
    }
}