package com.example.maket_kotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maket_kotlin.data.dto.AuthRequest
import kotlinx.coroutines.launch
import com.example.maket_kotlin.data.dto.RegistrationState
import com.example.maket_kotlin.service.AuthService
import com.example.maket_kotlin.service.AuthServiceImp
import kotlinx.coroutines.flow.StateFlow


class AuthViewModel(
    private val authService: AuthService = AuthServiceImp()) : ViewModel() {

    val registrationState: StateFlow<RegistrationState> = authService.authState

    fun auth(login: String, password: String) {
        val request = AuthRequest(login = login, password = password)

        viewModelScope.launch {
            authService.authUser(request)
        }
    }
}

