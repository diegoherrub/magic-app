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
import pol.rubiano.magicapp.app.domain.models.Card
import pol.rubiano.magicapp.app.domain.repositories.CardRepository
import pol.rubiano.magicapp.features.domain.models.Deck
import pol.rubiano.magicapp.features.domain.repositories.DeckRepository
import pol.rubiano.magicapp.features.domain.usecases.AddDeckUseCase
import pol.rubiano.magicapp.features.domain.usecases.GetDecksUseCase

@KoinViewModel
class DecksViewModel(
    private val getDecksUseCase: GetDecksUseCase,
    private val addDeckUseCase: AddDeckUseCase,
    private val repository: DeckRepository,
    private val cardRepository: CardRepository
) : ViewModel() {

//    private val _uiState = MutableLiveData<UiState>()
//    val uiState: LiveData<UiState> = _uiState

    private val _uiState = MutableLiveData(UiState(decks = emptyList()))
    val uiState: LiveData<UiState> = _uiState

    private val _selectedDeck = MutableLiveData<Deck?>()
    val selectedDeck: LiveData<Deck?> = _selectedDeck

    private val _deckCards = MutableLiveData<List<Card>>()
    val deckCards: LiveData<List<Card>> = _deckCards

//    fun loadDecks() {
//        _uiState.postValue(UiState(isLoading = true))

//        viewModelScope.launch(Dispatchers.IO) {
//            val decks = repository.getDecks()
//            withContext(Dispatchers.Main) {
//                _uiState.value = UiState(
//                    isLoading = false,
//                    decks = decks
//                )
//            }
//        }
//    }
    fun loadDecks() {
        viewModelScope.launch(Dispatchers.IO) {
            val decks = repository.getDecks()
            withContext(Dispatchers.Main) {
                _uiState.value = _uiState.value?.copy(decks = decks)
            }
        }
    }

    fun loadDeckById(deckId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val deck = repository.getDeckById(deckId)
            withContext(Dispatchers.Main) {
                _selectedDeck.value = deck
            }
        }
    }

    fun addDeck(deck: Deck) {
        //_uiState.postValue(UiState(isLoading = true))
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertDeck(deck)
            loadDecks()
        }
    }

    fun updateDeck(deck: Deck) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertDeck(deck)
            loadDecks()
        }
    }

    fun addCardToDeck(deckId: String, card: Card) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCardToDeck(deckId, card.id)
            // TODO Opcional: refrescar el deck si es necesario
            loadDeckById(deckId)
        }
    }

    fun loadDeckCards(deckId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val deck = repository.getDeckById(deckId)
            val cards = deck?.cardIds?.mapNotNull { cardId ->
                cardRepository.getCardById(cardId)
            } ?: emptyList()

            withContext(Dispatchers.Main) {
                _deckCards.value = cards
            }
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val errorApp: AppError? = null,
        val decks: List<Deck>? = null
    )
}
