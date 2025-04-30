package pol.rubiano.magicapp.features.randomcard.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import pol.rubiano.magicapp.app.domain.AppError
import pol.rubiano.magicapp.app.domain.UiState
import pol.rubiano.magicapp.features.cards.domain.models.Card
import pol.rubiano.magicapp.features.randomcard.domain.usecases.GetRandomCardUseCase

@KoinViewModel
class RandomCardViewModel(
    private val getRandomCardUseCase: GetRandomCardUseCase
) : ViewModel() {

    private val _randomCard = MutableLiveData<UiState<Card>>()
    val randomCard: LiveData<UiState<Card>> = _randomCard

    fun fetchRandomCard() {
        _randomCard.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = getRandomCardUseCase.invoke()
            result.onSuccess { card ->
                _randomCard.postValue(UiState.Success(card))
            }.onFailure { throwable ->
                UiState.Error(
                    if (throwable is AppError.NoResultsError) {
                        AppError.NoResultsError
                    } else {
                        AppError.AppDataError
                    }
                )
            }
        }
    }
}