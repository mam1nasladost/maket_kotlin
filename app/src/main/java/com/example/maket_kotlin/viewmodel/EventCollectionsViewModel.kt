package com.example.maket_kotlin.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maket_kotlin.data.dto.EventCollectionDto
import com.example.maket_kotlin.service.EventCollectionsService
import com.example.maket_kotlin.service.EventCollectionsServiceImp
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventCollectionsViewModel(private val service: EventCollectionsService = EventCollectionsServiceImp()): ViewModel() {

    private val _collectionsState = MutableStateFlow<List<EventCollectionDto>>(emptyList())
    val collectionsState: StateFlow<List<EventCollectionDto>> = _collectionsState

    fun loadCollections() {
        val handler = CoroutineExceptionHandler { _, exception ->
            Log.e("CollectionsViewModel", "Exception: $exception")
        }

        viewModelScope.launch(handler) {
            service.getCollections()
                .collect { collections ->
                    Log.d("CollectionsViewModel", "Collected: $collections")
                    _collectionsState.value = collections
                }
        }
    }
}