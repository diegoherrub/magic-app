package pol.rubiano.magicapp.features.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import pol.rubiano.magicapp.app.domain.ErrorApp
import pol.rubiano.magicapp.app.domain.Card
import pol.rubiano.magicapp.features.domain.GetSearchUseCase

@KoinViewModel
class SearchViewModel(
    private val getSearchUseCase: GetSearchUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> = _cards

    // Almacena la URL de la siguiente página
    private var nextPageUrl: String? = null

    fun fetchSearchCard(query: String) {
        _uiState.value = UiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = getSearchUseCase.invoke(query)
            result.onSuccess { scryfall ->
                _cards.postValue(scryfall.data.orEmpty())
                nextPageUrl = scryfall.nextPage
                _uiState.postValue(UiState(isLoading = false))
            }.onFailure {
                _uiState.postValue(UiState(isLoading = false, errorApp = ErrorApp.DataErrorApp))
            }
        }
    }

    // Nueva función para buscar usando la URL de la siguiente página
    fun fetchSearchPage(url: String) {
        _uiState.value = UiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = getSearchUseCase.loadNextPage(url)
            result.onSuccess { scryfall ->
                _cards.postValue(scryfall.data.orEmpty())
                nextPageUrl = scryfall.nextPage
                _uiState.postValue(UiState(isLoading = false))
            }.onFailure {
                _uiState.postValue(UiState(isLoading = false, errorApp = ErrorApp.DataErrorApp))
            }
        }
    }

    fun loadMoreCards() {
        if (nextPageUrl == null) return

        viewModelScope.launch(Dispatchers.IO) {
            val result = getSearchUseCase.loadNextPage(nextPageUrl!!)
            result.onSuccess { scryfall ->
                val currentList = _cards.value.orEmpty()
                _cards.postValue(currentList + scryfall.data.orEmpty())
                nextPageUrl = scryfall.nextPage
            }
        }
    }

    // Método para exponer la URL de la siguiente página
    fun getNextPageUrl(): String? = nextPageUrl

    data class UiState(
        val isLoading: Boolean = false,
        val errorApp: ErrorApp? = null
    )
}
