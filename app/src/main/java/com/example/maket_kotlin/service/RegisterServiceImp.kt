package com.example.maket_kotlin.service

import com.example.maket_kotlin.data.dto.RegisterRequest
import com.example.maket_kotlin.data.dto.RegistrationState
import com.example.maket_kotlin.network.BackendClient
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class RegisterServiceImp: RegisterService {
    private val backendClient = BackendClient()

    private val _registrationState = MutableStateFlow<RegistrationState>(RegistrationState.Idle)
    override val registrationState: StateFlow<RegistrationState> = _registrationState

    override suspend fun registerUser(request: RegisterRequest) {
        _registrationState.value = RegistrationState.Loading

        try {
            val response = backendClient.register(request)
            if (response.status.isSuccess()) {
                _registrationState.value = RegistrationState.Success
            } else {
                _registrationState.value = RegistrationState.Error("Ошибка: ${response.status}")
            }
        } catch (e: Exception) {
            _registrationState.value = RegistrationState.Error("Ошибка сети: ${e.localizedMessage}")
        }
    }
}

