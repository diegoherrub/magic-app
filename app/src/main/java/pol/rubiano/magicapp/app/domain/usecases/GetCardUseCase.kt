package pol.rubiano.magicapp.app.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pol.rubiano.magicapp.app.domain.AppError
import pol.rubiano.magicapp.app.domain.models.Card

class GetCardUseCase() : ViewModel() {

    private val _card = MutableLiveData<Card>()
    val card: LiveData<Card> get() = _card

    private val _uiError = MutableLiveData<AppError?>()
    val uiError: LiveData<AppError?> get() = _uiError

    fun setCard(card: Card) {
        _card.value = card
    }
}