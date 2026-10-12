package org.ucb.appp1.feature.announcements.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.appp1.feature.announcements.domain.usecase.AddAnnouncementUseCase
import org.ucb.appp1.feature.announcements.domain.usecase.DeleteAnnouncementUseCase
import org.ucb.appp1.feature.announcements.domain.usecase.ObserveAnnouncementsUseCase

class AnnouncementsViewModel(
    private val observeAnnouncements: ObserveAnnouncementsUseCase,
    private val addAnnouncement: AddAnnouncementUseCase,
    private val deleteAnnouncement: DeleteAnnouncementUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AnnouncementsState())
    val state = _state.asStateFlow()

    init {
        // Se queda escuchando: cada cambio en Firestore actualiza la pantalla sin hacer refresh.
        observeAnnouncements()
            .onEach { list ->
                _state.update { it.copy(isLoading = false, errorMessage = null, announcements = list) }
            }
            .catch { e ->
                _state.update { it.copy(isLoading = false, errorMessage = e.message ?: "Error de Firestore") }
            }
            .launchIn(viewModelScope)
    }

    fun emitEvent(event: AnnouncementsEvents) {
        when (event) {
            is AnnouncementsEvents.OnAdd -> add(event.title, event.message)
            is AnnouncementsEvents.OnDelete -> delete(event.id)
        }
    }

    private fun add(title: String, message: String) {
        if (title.isBlank()) {
            _state.update { it.copy(errorMessage = "Escribe un título") }
            return
        }
        viewModelScope.launch {
            addAnnouncement(title, message).onFailure { e ->
                _state.update { it.copy(errorMessage = e.message ?: "No se pudo guardar") }
            }
        }
    }

    private fun delete(id: String) {
        viewModelScope.launch {
            deleteAnnouncement(id).onFailure { e ->
                _state.update { it.copy(errorMessage = e.message ?: "No se pudo eliminar") }
            }
        }
    }
}
