package pol.rubiano.magicapp.features.presentation.ui

import androidx.navigation.fragment.findNavController

class EditCardFragment : DecksFragment() {
    override fun onClickCard(deckid: String) {
        findNavController().navigate(
            DecksFragmentDirections.actionDecksFragmentToDeckConfigFragment(
                deck.id
            )
        )
    }
}