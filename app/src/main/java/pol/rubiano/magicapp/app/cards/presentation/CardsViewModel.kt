package pol.rubiano.magicapp.app.cards.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.annotation.KoinViewModel
import pol.rubiano.magicapp.app.cards.domain.usecases.GetCardUseCase
import pol.rubiano.magicapp.app.cards.domain.usecases.SaveCardUseCase
import pol.rubiano.magicapp.app.domain.AppError
import pol.rubiano.magicapp.app.domain.UiState
import pol.rubiano.magicapp.app.domain.models.Card

@KoinViewModel
class CardsViewModel(
    private val saveCardUseCase: SaveCardUseCase,
    private val getCardUseCase: GetCardUseCase,
) : ViewModel() {

    private val _savedCardToLocal = MutableLiveData<UiState<Card>>()
    val savedCardToLocal: LiveData<UiState<Card>> = _savedCardToLocal

    private val _fetchedCard = MutableLiveData<UiState<Card?>>()
    val fetchedCard: MutableLiveData<UiState<Card?>> = _fetchedCard

    fun saveCardToLocal(card: Card) {
        _savedCardToLocal.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            if (!existsInLocal(card.id)) {
                try {
                    saveCardUseCase.invoke(card)
                    _savedCardToLocal.value = UiState.Success(card)
                } catch (e: Exception) {
                    _savedCardToLocal.value = UiState.Error(AppError.AppDataError)
                }
            }
        }
    }

    fun getCardById(cardId: String) {
        _fetchedCard.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedCard = getCardUseCase.invoke(cardId)
            _fetchedCard.value = UiState.Success(fetchedCard)
        }
    }

    private suspend fun existsInLocal(cardId: String): Boolean {
        return getCardUseCase.invoke(cardId) != null
    }
}
