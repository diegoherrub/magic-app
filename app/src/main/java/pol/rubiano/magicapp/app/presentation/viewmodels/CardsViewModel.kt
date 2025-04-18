package pol.rubiano.magicapp.app.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.annotation.KoinViewModel
import pol.rubiano.magicapp.app.data.local.CardLocalDataSource
import pol.rubiano.magicapp.app.domain.AppError
import pol.rubiano.magicapp.app.domain.UiState
import pol.rubiano.magicapp.app.domain.models.Card

@KoinViewModel
class CardsViewModel(
    private val localRepository: CardLocalDataSource,
//    private val remoteRepository: CardRemoteDataSource
) : ViewModel() {

    private val _savedCardToLocal = MutableLiveData<UiState<Card>>()
    val savedCardToLocal: LiveData<UiState<Card>> = _savedCardToLocal

    fun saveCardToLocal(card: Card) {
        _savedCardToLocal.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val cardAlreadyExistsInLocal = localRepository.getCardById(card.id)
            if (cardAlreadyExistsInLocal == null) {
                try {
                    val savedCardToLocal = localRepository.saveCardToLocal(card)
                    withContext(Dispatchers.Main) {
                        _savedCardToLocal.postValue(UiState.Success(savedCardToLocal))
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _savedCardToLocal.postValue(UiState.Error(AppError.AppDataError))
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    _savedCardToLocal.postValue(UiState.Success(cardAlreadyExistsInLocal))
                }
            }
        }
    }
}
