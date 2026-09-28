package org.ucb.appp1.feature.catalog.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.appp1.feature.catalog.domain.usecase.GetCatalogUseCase

class CatalogViewModel(
    private val getCatalogUseCase: GetCatalogUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CatalogState())
    val state = _state.asStateFlow()

    private val _effects = MutableSharedFlow<CatalogEffects>()
    val effects = _effects.asSharedFlow()

    init {
        emitEvent(CatalogEvents.OnLoadMovies)
    }

    private fun emitEffect(effect: CatalogEffects) {
        viewModelScope.launch { _effects.emit(effect) }
    }

    fun emitEvent(event: CatalogEvents) {
        when (event) {
            is CatalogEvents.OnLoadMovies -> fetchMovies()
            is CatalogEvents.OnMovieClicked ->
                emitEffect(CatalogEffects.NavigateToMovieDetail(event.movie.id.toString()))
        }
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            getCatalogUseCase().fold(
                onSuccess = { movies ->
                    _state.update { it.copy(isLoading = false, movies = movies) }
                },
                onFailure = { e ->
                    val msg = e.message ?: "Error desconocido"
                    _state.update { it.copy(isLoading = false, errorMessage = msg) }
                    emitEffect(CatalogEffects.ShowToast("Error al cargar el catálogo: $msg"))
                }
            )
        }
    }
}