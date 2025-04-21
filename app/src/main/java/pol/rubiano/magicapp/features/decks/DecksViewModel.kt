package pol.rubiano.magicapp.features.decks

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
import pol.rubiano.magicapp.app.cards.domain.repositories.CardsRepository
import pol.rubiano.magicapp.features.domain.models.Deck
import pol.rubiano.magicapp.features.domain.repositories.DeckRepository

@KoinViewModel
class DecksViewModel(
    private val deckRepository: DeckRepository,
    private val cardsRepository: CardsRepository
) : ViewModel() {

    private val _addedDeck = MutableLiveData<UiState<Deck>>()
    val addedDeck: LiveData<UiState<Deck>> = _addedDeck

    private val _fetchedCardsFromDeck = MutableLiveData<UiState<List<Card?>>>()
    val fetchedCardsFromDeck: LiveData<UiState<List<Card?>>> = _fetchedCardsFromDeck

    private val _userDecks = MutableLiveData<UiState<List<Deck>>>()
    val userDecks: LiveData<UiState<List<Deck>>> = _userDecks

    private val _currentDeck = MutableLiveData<UiState<Deck>>()
    val currentDeck: LiveData<UiState<Deck>> = _currentDeck

    private val _addedCardToDeck = MutableLiveData<UiState<Deck>>()
    val addedCardToDeck: LiveData<UiState<Deck>> = _addedCardToDeck


    fun loadUserDecks() {
        _userDecks.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userDecks = deckRepository.getUserDecks()
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
                deckRepository.insertDeck(deck)
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

    private fun loadDeckCards(deck: Deck) {
        _fetchedCardsFromDeck.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val cardsFromDeck = deck.cardIds.map { cardId ->
                    cardsRepository.getCardById(cardId)
                }
                    _fetchedCardsFromDeck.postValue(UiState.Success(cardsFromDeck))
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    UiState.Error(AppError.AppDataError)
                }
            }
        }
    }

    fun loadCurrentDeck(deck: Deck) {
        _currentDeck.value = UiState.Loading
        loadDeckCards(deck)
        _currentDeck.value = UiState.Success(deck)
    }

    fun addCardToDeck(deck: Deck, card: Card) {
        _addedCardToDeck.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deckRepository.addCardToDeck(deck.id, card.id)
                val actualizedDeck = deckRepository.getDeckById(deck.id)
                withContext(Dispatchers.Main) {
                    if (actualizedDeck != null) {
                        _addedCardToDeck.value = UiState.Success(actualizedDeck)
                    } else {
                        _addedCardToDeck.value = UiState.Error(AppError.AppDataError)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _addedCardToDeck.value = UiState.Error(AppError.AppDataError)
                }
            }
        }
    }

}

