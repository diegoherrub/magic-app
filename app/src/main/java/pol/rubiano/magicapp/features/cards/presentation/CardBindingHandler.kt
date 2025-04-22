package pol.rubiano.magicapp.features.cards.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import pol.rubiano.magicapp.app.common.extensions.loadUrl
import pol.rubiano.magicapp.app.cards.data.mapManaSymbols
import pol.rubiano.magicapp.features.cards.domain.models.Card
import pol.rubiano.magicapp.app.domain.toLegalityItemList
import pol.rubiano.magicapp.app.presentation.legalities.LegalitiesAdapter
import pol.rubiano.magicapp.databinding.CardFragmentViewBinding

class CardBindingHandler {
    private var currentCard: Card? = null
    private var isTwoFaced: Boolean = false
    private var isFront: Boolean = true

    fun bind(card: Card, binding: CardFragmentViewBinding) {
        currentCard = card
        isTwoFaced = (card.frontFace != null && card.backFace != null)
        isFront = true

        binding.apply {
            viewCardRarity.text = card.rarity
            viewCardSetName.text = card.setName
            card.legalities?.let { legalities ->
                if (binding.legalitiesList.adapter !is LegalitiesAdapter) {
                    binding.legalitiesList.adapter = LegalitiesAdapter()
                }
                (binding.legalitiesList.adapter as LegalitiesAdapter)
                    .submitList(legalities.toLegalityItemList())
            }
            if (card.frontFace != null && card.backFace != null) {
                binding.flipButton.visibility = View.VISIBLE
            } else {
                binding.flipButton.visibility = View.GONE
            }

            if (isTwoFaced) updateVariableFields(card.frontFace, card, binding)
            else updateVariableFields(null, card, binding)
        }
    }

    private fun updateVariableFields(
        face: Card.Face?,
        card: Card,
        binding: CardFragmentViewBinding
    ) {
        binding.apply {
            if (face != null) {
                viewCardName.text = face.faceName ?: card.name
                viewCardTipeLine.text = face.faceTypeLine ?: card.typeLine
                face.faceOracleText?.let {
                    viewCardOracleText.text = mapManaSymbols(viewCardOracleText.context, it)
                } ?: card.oracleText?.let {
                    viewCardOracleText.text = mapManaSymbols(viewCardOracleText.context, it)
                }
                val imageUrl = face.faceImageNormal ?: card.imageCrop
                imageUrl?.let { viewCardImage.loadUrl(it) }
            } else {
                viewCardName.text = card.name
                viewCardTipeLine.text = card.typeLine
                card.oracleText?.let {
                    viewCardOracleText.text = mapManaSymbols(viewCardOracleText.context, it)
                }
                card.imageCrop?.let { viewCardImage.loadUrl(it) }
            }
        }
    }

    fun flipCard(binding: CardFragmentViewBinding) {
        val card = currentCard ?: return
        if (!isTwoFaced) return

        isFront = !isFront
        binding.apply {
            if (isFront) {
                updateVariableFields(card.frontFace, card, binding)
            } else {
                updateVariableFields(card.backFace, card, binding)
            }
        }
    }

    fun setupLegalitiesRecyclerView(legalitiesList: RecyclerView) {
        val spanCount = 2
        val layoutManager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
        legalitiesList.layoutManager = layoutManager

        if (legalitiesList.adapter !is LegalitiesAdapter) {
            legalitiesList.adapter = LegalitiesAdapter()
        }
    }
}