package com.example.maket_kotlin.service

import com.example.maket_kotlin.data.dto.RegisterRequest
import com.example.maket_kotlin.data.dto.RegistrationState
import kotlinx.coroutines.flow.StateFlow

interface RegisterService {
    val registrationState: StateFlow<RegistrationState>
    suspend fun registerUser(request: RegisterRequest)
}