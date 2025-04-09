package pol.rubiano.magicapp.app.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.annotation.KoinViewModel
import pol.rubiano.magicapp.app.domain.AppError
import pol.rubiano.magicapp.app.domain.UiState
import pol.rubiano.magicapp.app.domain.models.Card
import pol.rubiano.magicapp.app.domain.repositories.CardRepository

@KoinViewModel
class CardsViewModel(
    private val cardRepository: CardRepository
) : ViewModel() {

    private val _savedCardToLocal = MutableLiveData<UiState<Card>>()
    val savedCardToLocal: LiveData<UiState<Card>> = _savedCardToLocal

    fun saveCardToLocal(card: Card) {
        _savedCardToLocal.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val savedCardToLocal = cardRepository.saveCardToLocal(card)
                withContext(Dispatchers.Main) {
                    UiState.Success(savedCardToLocal)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    UiState.Error(AppError.AppDataError)
                }
            }
        }
    }

    suspend fun getCardById(cardId: String): Card? {
        return withContext(Dispatchers.IO) {
            cardRepository.getCardById(cardId)
        }
    }
}
