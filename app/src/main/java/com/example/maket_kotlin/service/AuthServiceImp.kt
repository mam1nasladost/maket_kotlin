package com.example.maket_kotlin.service

import com.example.maket_kotlin.data.dto.AuthRequest
import com.example.maket_kotlin.data.dto.RegistrationState
import com.example.maket_kotlin.network.BackendClient
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthServiceImp: AuthService {

    private val backendClient = BackendClient()

    private val _authState = MutableStateFlow<RegistrationState>(RegistrationState.Idle)
    override val authState: StateFlow<RegistrationState> = _authState

    override suspend fun authUser(request: AuthRequest) {
        _authState.value = RegistrationState.Loading

        try {
            val response = backendClient.auth(request)
            if (response.status.isSuccess()) {
                _authState.value = RegistrationState.Success
            } else {
                _authState.value = RegistrationState.Error("Ошибка: ${response.status}")
            }
        } catch (e: Exception) {
            _authState.value = RegistrationState.Error("Ошибка сети: ${e.localizedMessage}")
        }
    }
}