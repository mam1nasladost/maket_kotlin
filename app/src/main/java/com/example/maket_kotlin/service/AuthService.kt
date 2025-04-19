package com.example.maket_kotlin.service

import com.example.maket_kotlin.data.dto.AuthRequest
import com.example.maket_kotlin.data.dto.RegistrationState
import kotlinx.coroutines.flow.StateFlow

interface AuthService {
    val authState: StateFlow<RegistrationState>
    suspend fun authUser(request: AuthRequest)
}