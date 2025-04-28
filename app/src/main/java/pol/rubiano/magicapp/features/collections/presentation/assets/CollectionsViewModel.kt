package pol.rubiano.magicapp.features.collections.presentation.assets

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
import pol.rubiano.magicapp.features.collections.domain.CardInCollection
import pol.rubiano.magicapp.features.collections.domain.Collection
import pol.rubiano.magicapp.features.collections.domain.usecases.AddCardToCollectionUseCase
import pol.rubiano.magicapp.features.collections.domain.usecases.GetCardsOfCollectionUseCase
import pol.rubiano.magicapp.features.collections.domain.usecases.GetCollectionUseCase
import pol.rubiano.magicapp.features.collections.domain.usecases.GetCollectionsUseCase
import pol.rubiano.magicapp.features.collections.domain.usecases.SaveCollectionUseCase

@KoinViewModel
class CollectionsViewModel(
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val getCollectionUseCase: GetCollectionUseCase,
    private val saveCollectionUseCase: SaveCollectionUseCase,
    private val addCardToCollectionUseCase: AddCardToCollectionUseCase,
    private val getCardsOfCollectionUseCase: GetCardsOfCollectionUseCase,
) : ViewModel() {

    private val _fetchedCollections = MutableLiveData<UiState<List<Collection>>>()
    val fetchedCollections: LiveData<UiState<List<Collection>>> = _fetchedCollections

    private val _currentCollection = MutableLiveData<UiState<Collection>>()
    val currentCollection: LiveData<UiState<Collection>> = _currentCollection

    private val _cardsInCurrentCollection = MutableLiveData<UiState<List<CardInCollection>>>()
    val cardsInCurrentCollection: LiveData<UiState<List<CardInCollection>>> =
        _cardsInCurrentCollection

    // TODO - poner que cuando se carge la colección, se añadan en su atributo cards la lista que debe devolver getcards de colección






    fun loadCollections() {
        _fetchedCollections.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userCollections = getCollectionsUseCase.invoke()
                withContext(Dispatchers.Main) {
                    if (userCollections.isNotEmpty()) {
                        _fetchedCollections.postValue(UiState.Success(userCollections))
                    } else {
                        _fetchedCollections.postValue(UiState.Empty)
                    }

                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _fetchedCollections.postValue(UiState.Error(AppError.AppDataError))
                }
            }
        }
    }

    fun loadCardsOfCollection(collectionName: String) {
        _cardsInCurrentCollection.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val cardsOfCollection = getCardsOfCollectionUseCase.invoke(collectionName)
                withContext(Dispatchers.Main) {
                    _cardsInCurrentCollection.postValue(UiState.Success(cardsOfCollection))
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _cardsInCurrentCollection.postValue(UiState.Error(AppError.AppDataError))
                }
            }
        }
    }

    fun createCollection(newCollectionName: String) {
        saveCollection(
            Collection(
                name = newCollectionName,
                order = 1,
                cards = emptyList()
            )
        )
    }

    private fun saveCollection(collection: Collection) {
        _currentCollection.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                saveCollectionUseCase.invoke(collection)
                withContext(Dispatchers.Main) {
                    _currentCollection.postValue(UiState.Success(collection))
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _currentCollection.postValue(UiState.Error(AppError.AppDataError))
                }
            }
        }
    }

    fun addCardToCollection(collectionName: String, cardId: String) {
        _currentCollection.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                addCardToCollectionUseCase.invoke(collectionName, cardId)
                withContext(Dispatchers.Main) {
                    val actualCollection = getCollectionUseCase.invoke(collectionName)
                    _currentCollection.value = UiState.Success(actualCollection)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _currentCollection.value = UiState.Error(AppError.AppDataError)
                }
            }
        }
    }
}