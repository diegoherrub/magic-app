package pol.rubiano.magicapp.app.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.annotation.KoinViewModel
import pol.rubiano.magicapp.app.domain.models.Card
import pol.rubiano.magicapp.app.domain.repositories.CardRepository

@KoinViewModel
class CardsViewModel(
    private val cardRepository: CardRepository
) : ViewModel() {

    fun saveCardToLocal(card: Card) {
        viewModelScope.launch(Dispatchers.IO) {
            cardRepository.saveCardToLocal(card)
        }
    }

    suspend fun getCardById(cardId: String): Card? {
        return withContext(Dispatchers.IO) {
            cardRepository.getCardById(cardId)
        }
    }
}
