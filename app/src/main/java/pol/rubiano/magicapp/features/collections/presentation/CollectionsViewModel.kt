package pol.rubiano.magicapp.features.collections.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.annotation.KoinViewModel
import pol.rubiano.magicapp.app.domain.AppError
import pol.rubiano.magicapp.app.domain.UiState
import pol.rubiano.magicapp.features.collections.data.CollectionsRepository
import pol.rubiano.magicapp.features.collections.domain.Collection

@KoinViewModel
class CollectionsViewModel(
    private val collectionsRepository: CollectionsRepository
) : ViewModel() {

    private val _userCollections = MutableLiveData<UiState<List<Collection>>>()
    val userCollections: LiveData<UiState<List<Collection>>> = _userCollections

    private val _currentCollection = MutableLiveData<UiState<Collection>>()
    val currentCollection: LiveData<UiState<Collection>> = _currentCollection

    fun loadCollections() {
        _userCollections.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userCollections = collectionsRepository.getCollections()
                withContext(Dispatchers.Main) {
                    _userCollections.value = if (userCollections.isNotEmpty()) {
                        UiState.Success(userCollections)
                    } else {
                        UiState.Empty
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _userCollections.value = UiState.Error(AppError.AppDataError)
                }
            }
        }
    }

    fun loadCollection(collectionName: String) {
        _currentCollection.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val collection = collectionsRepository.getCollection(collectionName)
                withContext(Dispatchers.Main) {
                    _currentCollection.value = UiState.Success(collection)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _currentCollection.value = UiState.Error(AppError.AppDataError)
                }
            }
        }
    }

    fun saveCollection(collection: Collection) {
        _currentCollection.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
//                Log.d("@pol", "start -> CollectionsViewModel.saveCollection()")
                collectionsRepository.insertCollection(collection)
//                Log.d("@pol", "end -> CollectionsViewModel.saveCollection()")
                withContext(Dispatchers.Main) {
//                    Log.d("@pol", "start -> CollectionsViewModel.UiState.Success(collection)")
                    _currentCollection.value = UiState.Success(collection)
//                    Log.d("@pol", "end -> CollectionsViewModel.UiState.Success(collection)")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _currentCollection.value = UiState.Error(AppError.AppDataError)
                }
            }
        }
    }
}