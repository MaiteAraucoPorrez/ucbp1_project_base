package org.ucb.appp1.feature.catalog.presentation.viewmodel

// presentation/CatalogViewModel.kt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.appp1.feature.catalog.domain.repository.CatalogRepository

class CatalogViewModel(
    private val repository: CatalogRepository
) : ViewModel() {

    // --- State ---
    private val _state = MutableStateFlow(CatalogState())
    val state = _state.asStateFlow()

    // --- Effects ---
    private val _effects = MutableSharedFlow<CatalogEffects>()
    val effects = _effects.asSharedFlow()

    init {
        // Cargar las películas apenas se inicializa el ViewModel
        emitEvent(CatalogEvents.OnLoadMovies)
    }

    // --- Emisor de Efectos ---
    private fun emitEffect(effect: CatalogEffects) {
        viewModelScope.launch {
            _effects.emit(effect)
        }
    }

    // --- Manejador de Eventos ---
    fun emitEvent(event: CatalogEvents) {
        when (event) {
            is CatalogEvents.OnLoadMovies -> {
                fetchMovies()
            }
            is CatalogEvents.OnMovieClicked -> {
                // Ejemplo: Emitir efecto para navegar a detalles
                // Asumiendo que el título sirve como ID para el ejemplo,
                // lo ideal sería que MovieModel tuviera un 'id'
                emitEffect(CatalogEffects.NavigateToMovieDetail(event.movie.title))
            }
        }
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            repository.getMovies().fold(
                onSuccess = { moviesList ->
                    _state.update {
                        it.copy(isLoading = false, movies = moviesList)
                    }
                },
                onFailure = { exception ->
                    val errorMsg = exception.message ?: "Unknown error occurred"
                    _state.update {
                        it.copy(isLoading = false, errorMessage = errorMsg)
                    }
                    emitEffect(CatalogEffects.ShowToast("Error loading catalog: $errorMsg"))
                }
            )
        }
    }
}