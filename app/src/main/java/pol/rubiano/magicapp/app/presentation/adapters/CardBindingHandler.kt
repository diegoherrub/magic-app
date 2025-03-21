package pol.rubiano.magicapp.app.presentation.adapters

import pol.rubiano.magicapp.app.common.extensions.loadUrl
import pol.rubiano.magicapp.app.data.mapManaSymbols
import pol.rubiano.magicapp.app.domain.entities.Card
import pol.rubiano.magicapp.databinding.RandomCardFragmentBinding

class CardBindingHandler {
    private var currentCard: Card? = null
    private var isTwoFaced: Boolean = false
    private var isFront: Boolean = true

    fun bind(card: Card, binding: RandomCardFragmentBinding) {
        currentCard = card
        isTwoFaced = (card.frontFace != null && card.backFace != null)
        isFront = true

        binding.apply {
            randomCardRarity.text = card.rarity
            randomCardSetName.text = card.setName

            if (isTwoFaced) {
                updateVariableFields(card.frontFace, card, binding)
            } else {
                updateVariableFields(null, card, binding)
            }
        }
    }

    private fun updateVariableFields(
        face: Card.Face?,
        card: Card,
        binding: RandomCardFragmentBinding
    ) {
        binding.apply {
            if (face != null) {
                randomCardName.text = face.faceName ?: card.name
                randomCardTipeLine.text = face.faceTypeLine ?: card.typeLine
                face.faceOracleText?.let {
                    randomCardOracleText.text = mapManaSymbols(randomCardOracleText.context, it)
                } ?: card.oracleText?.let {
                    randomCardOracleText.text = mapManaSymbols(randomCardOracleText.context, it)
                }
                val imageUrl = face.faceImageNormal ?: card.imageNormal
                imageUrl?.let { randomCardImage.loadUrl(it) }
            } else {
                randomCardName.text = card.name
                randomCardTipeLine.text = card.typeLine
                card.oracleText?.let {
                    randomCardOracleText.text = mapManaSymbols(randomCardOracleText.context, it)
                }
                card.imageNormal?.let { randomCardImage.loadUrl(it) }
            }
        }
    }

    fun flipCard(binding: RandomCardFragmentBinding) {
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
}