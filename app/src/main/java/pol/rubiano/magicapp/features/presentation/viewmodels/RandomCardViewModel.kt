package pol.rubiano.magicapp.features.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import pol.rubiano.magicapp.app.domain.ErrorApp
import pol.rubiano.magicapp.app.domain.Card
import pol.rubiano.magicapp.features.domain.GetRandomCardUseCase

@KoinViewModel
class RandomCardViewModel(
    private val getUseCase: GetRandomCardUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    fun fetchRandomCard() {
        _uiState.value = UiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = getUseCase.invoke()
            result.onSuccess { card ->
                _uiState.postValue(
                    UiState(
                        isLoading = false,
                        card = card
                    )
                )
            }
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val errorApp: ErrorApp? = null,
        val card: Card? = null
    )
}