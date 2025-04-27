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
import pol.rubiano.magicapp.features.cards.domain.repositories.CardRepository
import pol.rubiano.magicapp.features.decks.domain.models.Deck
import pol.rubiano.magicapp.features.decks.domain.repositories.DecksRepository
import pol.rubiano.magicapp.features.decks.domain.usecases.CreateNewDeckUseCase
import pol.rubiano.magicapp.features.decks.domain.usecases.GetDeckUseCase
import pol.rubiano.magicapp.features.decks.domain.usecases.GetDecksUseCase

@KoinViewModel
class DecksViewModel(
    private val createNewDeckUseCase: CreateNewDeckUseCase,
    private val getDeckUseCase: GetDeckUseCase,
    private val getDecksUseCase: GetDecksUseCase,



    private val decksRepository: DecksRepository,
    private val cardRepository: CardRepository
) : ViewModel() {

    private val _newDeckCreated = MutableLiveData<UiState<Deck>>()
    val newDeckCreated: LiveData<UiState<Deck>> = _newDeckCreated

    private val _fetchedDeck = MutableLiveData<UiState<Deck>>()
    val fetchedDeck: LiveData<UiState<Deck>> = _fetchedDeck

    private val _userDecks = MutableLiveData<UiState<List<Deck>>>()
    val userDecks: LiveData<UiState<List<Deck>>> = _userDecks




    private val _addedDeck = MutableLiveData<UiState<Deck>>()
    val addedDeck: LiveData<UiState<Deck>> = _addedDeck

    private val _fetchedCardsFromDeck = MutableLiveData<UiState<List<Card?>>>()
    val fetchedCardsFromDeck: LiveData<UiState<List<Card?>>> = _fetchedCardsFromDeck



    private val _addedCardToDeck = MutableLiveData<UiState<Deck>>()
    val addedCardToDeck: LiveData<UiState<Deck>> = _addedCardToDeck



    fun createNewDeck(newDeckName: String, newDeckDescription: String) {
        _newDeckCreated.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newDeck = createNewDeckUseCase.invoke(newDeckName, newDeckDescription)
                withContext(Dispatchers.Main) {
                    _newDeckCreated.postValue(UiState.Success(newDeck))
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _newDeckCreated.postValue(UiState.Error(AppError.AppDataError))
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

    fun loadUserDecks() {
        _userDecks.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userDecks = decksRepository.getUserDecks()
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








    private fun saveDeck(deck: Deck) {
        _addedDeck.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                decksRepository.saveDeck(deck)
                withContext(Dispatchers.Main) {
                    _addedDeck.value = UiState.Success(deck)
                    _fetchedDeck.value = _addedDeck.value
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
                    cardRepository.getLocalCardById(cardId)
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
        _fetchedDeck.value = UiState.Loading
        loadDeckCards(deck)
        _fetchedDeck.value = UiState.Success(deck)
    }

    fun addCardToDeck(deck: Deck, card: Card) {
        _addedCardToDeck.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                decksRepository.addCardToDeck(deck.id, card.id)
                val actualizedDeck = decksRepository.getDeckById(deck.id)
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

