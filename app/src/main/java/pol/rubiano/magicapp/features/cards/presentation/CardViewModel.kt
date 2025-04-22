package pol.rubiano.magicapp.features.cards.presentation

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
import pol.rubiano.magicapp.features.cards.domain.models.Card
import pol.rubiano.magicapp.features.cards.domain.usecases.SaveCardUseCase

@KoinViewModel
class CardViewModel(
    private val saveCardUseCase: SaveCardUseCase,
) : ViewModel() {

    private val _card = MutableLiveData<UiState<Card>>()
    var card: LiveData<UiState<Card>> = _card

    suspend fun saveCardToLocal(card: Card) {
        _card.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                saveCardUseCase.invoke(card)
                withContext(Dispatchers.Main) {
                    _card.postValue(UiState.Success(card))
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _card.postValue(UiState.Error(AppError.AppDataError))
                }
            }
        }
    }
}