package com.example.maket_kotlin.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maket_kotlin.data.dto.eventshortdto
import com.example.maket_kotlin.service.EventService
import com.example.maket_kotlin.service.EventServiceImp
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventViewModel(private val eventService: EventService = EventServiceImp()) : ViewModel() {
    private val _cardsState = MutableStateFlow<List<eventshortdto>>(emptyList())
    val cardsState: StateFlow<List<eventshortdto>> = _cardsState

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex

    fun loadCards() {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            Log.e("EventViewModel", "exceptionhandler got $exception")
        }

        viewModelScope.launch(exceptionHandler) {
            eventService.getEvents()
                .collect { events ->
                    _cardsState.value = events
                    if (events.isNotEmpty()) {
                        _currentIndex.value = 0
                    }
                }
        }
    }

    fun updateIndex(newIndex: Int) {
        _currentIndex.value = newIndex
    }

    fun sendLike(eventId: Int) {
        viewModelScope.launch() {
            eventService.sendLike(eventId)
        }
    }
}