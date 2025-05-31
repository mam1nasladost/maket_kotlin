package com.example.maket_kotlin.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maket_kotlin.data.dto.UserResponse
import com.example.maket_kotlin.data.dto.EventShortDto
import com.example.maket_kotlin.data.dto.EventStatusDto
import com.example.maket_kotlin.network.BackendClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _user = MutableStateFlow<UserResponse?>(null)
    val user: StateFlow<UserResponse?> = _user

    private val _events = MutableStateFlow<List<EventStatusDto>>(emptyList())
    val events: StateFlow<List<EventStatusDto>> = _events

    private val backendClient = BackendClient()

    init {
        fetchUser()
    }

    private fun fetchUser() {
        viewModelScope.launch {
            try {
                val userInfo = backendClient.getUserInfo()
                _user.value = userInfo
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Ошибка при получении профиля", e)
            }
        }
    }


    fun loadUserEvents() {
        viewModelScope.launch {
            try {

                val eventRequests = backendClient.getUserEventsId()
                val result = mutableListOf<EventStatusDto>()
                for (request in eventRequests) {
                    val event = backendClient.getEventById(request.id)
                    result.add(EventStatusDto(event, request.status))
                }
                _events.value = result
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Ошибка при получении мероприятий пользователя", e)
            }
        }
    }
}