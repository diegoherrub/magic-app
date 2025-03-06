package pol.rubiano.magicapp.app.common.extensions

import pol.rubiano.magicapp.app.data.mapManaSymbols
import pol.rubiano.magicapp.app.domain.entities.Card
import pol.rubiano.magicapp.databinding.RandomCardFragmentBinding

class CardBindingHandler {

    fun bind(card: Card, binding: RandomCardFragmentBinding) {
        binding.apply {
            card.borderCrop?.let { randomCardImage.loadUrl(it) }
            randomCardName.text = card.name
            randomCardTipeLine.text = card.typeLine
            randomCardRarity.text = card.rarity
            randomCardSetName.text = card.setName

            card.oracleText?.let { cost ->
                randomCardOracleText.text = mapManaSymbols(randomCardOracleText.context, cost)
            }
        }
    }
}