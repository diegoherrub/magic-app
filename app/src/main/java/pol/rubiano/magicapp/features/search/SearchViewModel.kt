package pol.rubiano.magicapp.features.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import pol.rubiano.magicapp.app.domain.AppError
import pol.rubiano.magicapp.app.domain.UiState
import pol.rubiano.magicapp.app.domain.models.Card
import pol.rubiano.magicapp.features.domain.usecases.GetSearchUseCase

@KoinViewModel
class SearchViewModel(
    private val getSearchUseCase: GetSearchUseCase
) : ViewModel() {

    private val _cards = MutableLiveData<UiState<List<Card>>>()
    val cards: MutableLiveData<UiState<List<Card>>> = _cards

    private var nextPageUrl: String? = null
    var lastQueryAttempt: String? = null
        private set

    private var isLoadingMore: Boolean = false

    fun fetchSearchCard(query: String) {
        lastQueryAttempt = query
        _cards.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = getSearchUseCase.invoke(query)
            result.onSuccess { scryfall ->
                _cards.postValue(UiState.Success(scryfall.data.orEmpty()))
                nextPageUrl = scryfall.nextPage
            }.onFailure { throwable ->
                _cards.postValue(
                    UiState.Error(
                        if (throwable is AppError.NoResultsError) {
                            AppError.NoResultsError
                        } else {
                            AppError.AppDataError
                        }
                    )
                )
            }
        }
    }

    fun fetchSearchPage(url: String) {
        lastQueryAttempt = url
        _cards.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = getSearchUseCase.loadNextPage(url)
            result.onSuccess { scryfall ->
                _cards.postValue(UiState.Success(scryfall.data.orEmpty()))
                nextPageUrl = scryfall.nextPage
            }.onFailure { throwable ->
                _cards.postValue(
                    UiState.Error(
                        if (throwable is AppError.NoResultsError) {
                            AppError.NoResultsError
                        } else {
                            AppError.AppDataError
                        }
                    )
                )
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
                val currentList = (_cards.value as? UiState.Success)?.data.orEmpty()
                _cards.postValue(UiState.Success(currentList + scryfall.data.orEmpty()))
                nextPageUrl = scryfall.nextPage
                isLoadingMore = false
            }.onFailure { throwable ->
                _cards.postValue(
                    UiState.Error(
                        if (throwable is AppError.NoResultsError) {
                            AppError.NoResultsError
                        } else {
                            AppError.AppDataError
                        }
                    )
                )
                isLoadingMore = false
            }
        }
    }
}