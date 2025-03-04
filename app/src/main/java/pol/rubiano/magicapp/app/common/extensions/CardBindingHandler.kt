package pol.rubiano.magicapp.app.common.extensions

import pol.rubiano.magicapp.app.domain.entities.Card
import pol.rubiano.magicapp.databinding.RandomCardFragmentBinding

class CardBindingHandler {

    fun bind(card: Card, binding: RandomCardFragmentBinding) {
        binding.apply {
            card.borderCrop?.let { randomCardImage.loadUrl(it) }
            randomCardName.text = card.name
            randomCardTipeLine.text = card.typeLine
            randomCardRarity.text = card.rarity
            randomCardOracleText.text = card.oracleText
            randomCardSetName.text = card.setName
        }
    }
}