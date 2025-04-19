package com.example.maket_kotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maket_kotlin.data.dto.RegisterRequest
import kotlinx.coroutines.launch
import com.example.maket_kotlin.data.dto.RegistrationState
import com.example.maket_kotlin.service.RegisterService
import com.example.maket_kotlin.service.RegisterServiceImp
import kotlinx.coroutines.flow.StateFlow


class RegistrationViewModel(
    private val registerService: RegisterService = RegisterServiceImp()
) : ViewModel() {

    val registrationState: StateFlow<RegistrationState> = registerService.registrationState

    fun register(login: String, password: String, name: String, surname: String) {
        val request = RegisterRequest(login = login, password = password, name = name, surname = surname, groupName = "")

        viewModelScope.launch {
            registerService.registerUser(request)
        }
    }
}

