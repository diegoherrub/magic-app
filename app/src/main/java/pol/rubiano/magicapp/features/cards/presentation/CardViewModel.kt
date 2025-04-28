package pol.rubiano.magicapp.features.cards.presentation

import android.util.Log
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
import pol.rubiano.magicapp.features.cards.domain.usecases.AddCardToCollectionUseCase
import pol.rubiano.magicapp.features.cards.domain.usecases.SaveCardInCollectionUseCase
import pol.rubiano.magicapp.features.cards.domain.usecases.SaveCardUseCase
import pol.rubiano.magicapp.features.collections.domain.CardInCollection

@KoinViewModel
class CardViewModel(
    private val addCardToCollectionUseCase: AddCardToCollectionUseCase,
    //private val saveCardUseCase: SaveCardUseCase,
    //private val saveCardInCollectionUseCase: SaveCardInCollectionUseCase
) : ViewModel() {

    private val _card = MutableLiveData<UiState<Card>>()
    var card: LiveData<UiState<Card>> = _card

    private val _addedCardToCollection = MutableLiveData<UiState<Boolean>>()
//    var addedCardToCollection: LiveData<UiState<CardInCollection>> = _addedCardToCollection



    fun addCardToCollection(card: Card, collectionName: String) {
        _addedCardToCollection.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                addCardToCollectionUseCase.invoke(card, collectionName)
                withContext(Dispatchers.Main) {
                    _addedCardToCollection.postValue(UiState.Success(true)) // TODO - REVISAR SI NECESITO DEVOLVER OTRA COSA
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _addedCardToCollection.postValue(UiState.Error(AppError.AppDataError))
                }
            }
        }
    }



    private fun saveCardToLocal(card: Card) {
        // // TODO - COMPROBAR QUE SI YA EXISTE EN LOCAL PARA EVITAR BAJAR DE NUEVO LOS DATOS
        // _card.value = UiState.Loading
        // viewModelScope.launch(Dispatchers.IO) {
        //     try {
        //         saveCardUseCase.invoke(card)
        //         withContext(Dispatchers.Main) {
        //             _card.postValue(UiState.Success(card))
        //         }
        //     } catch (e: Exception) {
        //         withContext(Dispatchers.Main) {
        //             _card.postValue(UiState.Error(AppError.AppDataError))
        //         }
        //     }
        // }
    }

    private fun saveCardsInCollectionToLocal(cardInCollection: CardInCollection) {
        // _addedCardToCollection.value = UiState.Loading
        // viewModelScope.launch(Dispatchers.IO) {
        //     try {
        //         saveCardInCollectionUseCase.invoke(cardInCollection)
        //         withContext(Dispatchers.Main) {
        //             _addedCardToCollection.postValue(UiState.Success(cardInCollection))
        //         }
        //     } catch (e: Exception) {
        //         withContext(Dispatchers.Main) {
        //             _addedCardToCollection.postValue(UiState.Error(AppError.AppDataError))
        //         }
        //     }
        // }
    }
}