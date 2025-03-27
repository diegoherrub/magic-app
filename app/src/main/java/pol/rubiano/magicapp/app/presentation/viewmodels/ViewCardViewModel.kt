package pol.rubiano.magicapp.app.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.koin.android.annotation.KoinViewModel
import pol.rubiano.magicapp.app.domain.AppError
import pol.rubiano.magicapp.app.domain.Card


@KoinViewModel
class ViewCardViewModel : ViewModel() {

    private val _card = MutableLiveData<Card>()
    val card: LiveData<Card> get() = _card

    private val _uiError = MutableLiveData<AppError?>()
    val uiError: LiveData<AppError?> get() = _uiError

    fun setCard(card: Card) {
        _card.value = card
    }

}