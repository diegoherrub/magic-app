ckage pol.rubiano.magicapp.features.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import pol.rubiano.magicapp.app.domain.AppError
import pol.rubiano.magicapp.features.domain.entities.Deck
import pol.rubiano.magicapp.features.domain.repositories.DeckRepository
import pol.rubiano.magicapp.features.domain.usecases.AddDeckUseCase
import pol.rubiano.magicapp.features.domain.usecases.GetDecksUseCase

@KoinViewModel
class DeckViewModel(
    private val getDecksUseCase: GetDecksUseCase,
    private val addDeckUseCase: AddDeckUseCase,
    private val repository: DeckRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    init {
        loadDecks()
    }

    fun loadDecks() {
        _uiState.value = UiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val decks = repository.getDecks()
                _uiState.postValue(
                    UiState(
                        isLoading = false,
                        decks = decks
                    )
                )
        }
    }

    fun addDeck(deck: Deck) {
        _uiState.value = UiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertDeck(deck)
            loadDecks()
        }
    }

    suspend fun getDeckById(deckId: String): Deck? {
        return repository.getDeckById(deckId)
    }

    fun updateDeck(deck: Deck) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertDeck(deck)
            loadDecks()
        }
    }

//    fun addCardToSideboard(deck: Deck, cardId: String): Deck {
//        return if (deck.sideBoard.size < 15) {
//            deck.copy(sideBoard = deck.sideBoard + cardId)
//        } else {
//            // TODO notify the user that the sideboard is full
//            deck
//        }
//    }

    data class UiState(
        val isLoading: Boolean = false,
        val errorApp: AppError? = null,
        val decks: List<Deck>? = null
    )
}
