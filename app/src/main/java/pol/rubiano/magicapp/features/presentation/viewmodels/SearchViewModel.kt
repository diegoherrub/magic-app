package pol.rubiano.magicapp.features.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import pol.rubiano.magicapp.app.domain.AppError
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

    // Store URL for the next page and last query attempted
    private var nextPageUrl: String? = null
    var lastQueryAttempt: String? = null
        private set

    // Flag to prevent multiple simultaneous pagination requests
    private var isLoadingMore: Boolean = false

    fun fetchSearchCard(query: String) {
        lastQueryAttempt = query
        _uiState.value = UiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = getSearchUseCase.invoke(query)
            result.onSuccess { scryfall ->
                _cards.postValue(scryfall.data.orEmpty())
                nextPageUrl = scryfall.nextPage
                _uiState.postValue(UiState(isLoading = false))
            }.onFailure {
                _uiState.postValue(UiState(isLoading = false, appError = AppError.AppDataError))
            }
        }
    }

    fun fetchSearchPage(url: String) {
        lastQueryAttempt = url
        _uiState.value = UiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = getSearchUseCase.loadNextPage(url)
            result.onSuccess { scryfall ->
                _cards.postValue(scryfall.data.orEmpty())
                nextPageUrl = scryfall.nextPage
                _uiState.postValue(UiState(isLoading = false))
            }.onFailure {
                _uiState.postValue(UiState(isLoading = false, appError = AppError.AppDataError))
            }
        }
    }

    fun loadMoreCards() {
        if (nextPageUrl == null || isLoadingMore) return

        isLoadingMore = true
        lastQueryAttempt = nextPageUrl
        viewModelScope.launch(Dispatchers.IO) {
            val result = getSearchUseCase.loadNextPage(nextPageUrl!!)
            result.onSuccess { scryfall ->
                val currentList = _cards.value.orEmpty()
                _cards.postValue(currentList + scryfall.data.orEmpty())
                nextPageUrl = scryfall.nextPage
                isLoadingMore = false
            }.onFailure {
                _uiState.postValue(UiState(isLoading = false, appError = AppError.AppDataError))
                isLoadingMore = false
            }
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val appError: AppError? = null,
        val cards: LiveData<List<Card>>? = null
    )
}
