package pol.rubiano.magicapp.features.decks.presentation.assets

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.annotation.KoinViewModel
import pol.rubiano.magicapp.app.domain.AppError
import pol.rubiano.magicapp.app.domain.UiState
import pol.rubiano.magicapp.features.cards.domain.models.Card
import pol.rubiano.magicapp.features.decks.domain.models.Deck
import pol.rubiano.magicapp.features.decks.domain.usecases.GetCardsInDeckUseCase
import pol.rubiano.magicapp.features.decks.domain.usecases.GetDeckUseCase
import pol.rubiano.magicapp.features.decks.domain.usecases.GetDecksUseCase
import pol.rubiano.magicapp.features.decks.domain.usecases.SaveCardInDeckUseCase
import pol.rubiano.magicapp.features.decks.domain.usecases.SaveDeckUseCase
import java.util.UUID

@KoinViewModel
class DecksViewModel(
    private val saveDeckUseCase: SaveDeckUseCase,
    private val getDecksUseCase: GetDecksUseCase,
    private val saveCardInDeckUseCase: SaveCardInDeckUseCase,
    private val getDeckUseCase: GetDeckUseCase,
    private val getCardsInDeckUseCase: GetCardsInDeckUseCase,
) : ViewModel() {

    private val _currentDeck = MutableLiveData<UiState<Deck>>()
    private val _fetchedDecks = MutableLiveData<UiState<List<Deck>>>()
    private val _fetchedDeck = MutableLiveData<UiState<Deck>>()
    private val _savedCardInDeck = MutableLiveData<UiState<Deck>>()

    val currentDeck: LiveData<UiState<Deck>> = _currentDeck
    val fetchedDecks: LiveData<UiState<List<Deck>>> = _fetchedDecks
    val fetchedDeck: LiveData<UiState<Deck>> = _fetchedDeck
    val savedCardInDeck: LiveData<UiState<Deck>> = _savedCardInDeck


    private val _fetchedCardsFromDeck = MutableLiveData<UiState<List<Card?>>>()
    val fetchedCardsFromDeck: LiveData<UiState<List<Card?>>> = _fetchedCardsFromDeck

    fun createNewDeck(newDeckName: String, newDeckDescription: String) {
        saveDeck(
            Deck(
                id = UUID.randomUUID().toString(),
                name = newDeckName,
                description = newDeckDescription,
                colors = emptyList(),
                cards = emptyList()
            )
        )
    }

    private fun saveDeck(deck: Deck) {
        _currentDeck.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val createdDeck = saveDeckUseCase.invoke(deck)
                withContext(Dispatchers.Main) {
                    _currentDeck.postValue(UiState.Success(createdDeck))
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _currentDeck.postValue(UiState.Error(AppError.AppDataError))
                }
            }
        }
    }

    fun loadDecks() {
        _fetchedDecks.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userDecks = getDecksUseCase.invoke()
                withContext(Dispatchers.Main) {
                    if (userDecks.isNotEmpty()) {
                        _fetchedDecks.postValue(UiState.Success(userDecks))
                    } else {
                        _fetchedDecks.postValue(UiState.Empty)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _fetchedDecks.postValue(UiState.Error(AppError.AppDataError))
                }
            }
        }
    }

    fun getDeckById(deckId: String) {
        _fetchedDeck.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val deck = getDeckUseCase.invoke(deckId)
                if (deck != null) {
                    withContext(Dispatchers.Main) {
                        _fetchedDeck.postValue(UiState.Success(deck))
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        _fetchedDeck.postValue(UiState.Empty)
                    }
                }
            } catch (e: Exception) {
                _fetchedDeck.postValue(UiState.Error(AppError.AppDataError))
            }
        }
    }

    fun saveCardToDeck(cardId: String, deckId: String) {
        _savedCardInDeck.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updatedDeck = saveCardInDeckUseCase.invoke(cardId, deckId)
                withContext(Dispatchers.Main) {
                    _savedCardInDeck.postValue(UiState.Success(updatedDeck))
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _savedCardInDeck.postValue(UiState.Error(AppError.AppDataError))
                }
            }
        }
    }


    //private fun loadDeckCards(deck: Deck) {
    //    _fetchedCardsFromDeck.value = UiState.Loading
    //    viewModelScope.launch(Dispatchers.IO) {
    //        try {
    //            val cardsFromDeck = deck.cardIds.map { cardId ->
    //                cardRepository.getCard(cardId)
    //            }
    //            _fetchedCardsFromDeck.postValue(UiState.Success(cardsFromDeck))
    //        } catch (e: Exception) {
    //            withContext(Dispatchers.Main) {
    //                UiState.Error(AppError.AppDataError)
    //            }
    //        }
    //    }
    //}

    //fun loadCurrentDeck(deck: Deck) {
    //    _fetchedDeck.value = UiState.Loading
    //    loadDeckCards(deck)
    //    _fetchedDeck.value = UiState.Success(deck)
    //}

}

