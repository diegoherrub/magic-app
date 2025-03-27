package pol.rubiano.magicapp.app.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GetCardUseCase() : ViewModel() {

    private val _card = MutableLiveData<Card>()
    val card: LiveData<Card> get() = _card

    // Si lo deseas, puedes tener un LiveData para errores
    private val _uiError = MutableLiveData<pol.rubiano.magicapp.app.domain.AppError?>()
    val uiError: LiveData<pol.rubiano.magicapp.app.domain.AppError?> get() = _uiError

    fun setCard(card: Card) {
        _card.value = card
    }
}