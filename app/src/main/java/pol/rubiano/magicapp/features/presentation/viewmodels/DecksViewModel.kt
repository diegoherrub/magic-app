package pol.rubiano.magicapp.features.presentation.viewmodels

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
import pol.rubiano.magicapp.app.domain.models.Card
import pol.rubiano.magicapp.app.domain.repositories.CardRepository
import pol.rubiano.magicapp.features.domain.models.Deck
import pol.rubiano.magicapp.features.domain.repositories.DeckRepository

@KoinViewModel
class DecksViewModel(
    private val repository: DeckRepository,
    private val cardRepository: CardRepository
) : ViewModel() {

    private val _addedDeck = MutableLiveData<UiState<Deck>>()
    val addedDeck: LiveData<UiState<Deck>> = _addedDeck

    private val _fetchedCardsFromDeck = MutableLiveData<UiState<List<Card>>>()
    val fetchedCardsFromDeck: LiveData<UiState<List<Card>>> = _fetchedCardsFromDeck

    private val _updatedDeck = MutableLiveData<UiState<Deck>>()
    val updatedDeck: LiveData<UiState<Deck>> = _updatedDeck

    private val _userDecks = MutableLiveData<UiState<List<Deck>>>()
    val userDecks: LiveData<UiState<List<Deck>>> = _userDecks

    private val _currentDeck = MutableLiveData<UiState<Deck>>()
    val currentDeck: LiveData<UiState<Deck>> = _currentDeck


    fun loadUserDecks() {
        _userDecks.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userDecks = repository.getUserDecks()
                withContext(Dispatchers.Main) {
                    _userDecks.value = if (userDecks.isNotEmpty()) {
                        UiState.Success(userDecks)
                    } else {
                        UiState.Empty
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    UiState.Error(AppError.AppDataError)
                }
            }
        }
    }

    fun addDeck(deck: Deck) {
        _addedDeck.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.insertDeck(deck)
                withContext(Dispatchers.Main) {
                    _addedDeck.value = UiState.Success(deck)
                    _currentDeck.value = _addedDeck.value
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    UiState.Error(AppError.AppDataError)
                }
            }
        }
    }

    fun updateDeck(deck: Deck) {
        _updatedDeck.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.insertDeck(deck)
                withContext(Dispatchers.Main) {
                    _updatedDeck.value = UiState.Success(deck)
                    _currentDeck.value = _updatedDeck.value
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    UiState.Error(AppError.AppDataError)
                }
            }
        }
    }

    fun loadDeckCards(deck: Deck) {
        _fetchedCardsFromDeck.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val cardsFromDek = deck.cardIds.mapNotNull { cardId ->
                    cardRepository.getCardById(cardId)
                }
                _fetchedCardsFromDeck.value = UiState.Success(cardsFromDek)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    UiState.Error(AppError.AppDataError)
                }
            }
        }
    }

    fun loadCurrentDeck(deck: Deck) {
        _currentDeck.value = UiState.Success(deck)
        loadDeckCards(deck)
    }
}

