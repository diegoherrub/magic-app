package pol.rubiano.magicapp.app.domain

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data object Empty : UiState<Nothing>()
    data class Error(val error: AppError) : UiState<Nothing>()
}