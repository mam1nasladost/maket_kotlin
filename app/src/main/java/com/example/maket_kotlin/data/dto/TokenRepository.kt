package com.example.maket_kotlin.data.dto

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object TokenRepository {
    private lateinit var sharedPreferences: SharedPreferences
    private val _tokenFlow = MutableStateFlow<String?>(null)
    val tokenFlow: StateFlow<String?> get() = _tokenFlow

    fun init(context: Context) {
        if (!::sharedPreferences.isInitialized) {
            sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
            _tokenFlow.value = token // инициализируем текущим значением
        }
    }

    var token: String?
        get() = sharedPreferences.getString("TOKEN", null)
        set(value) {
            sharedPreferences.edit().putString("TOKEN", value).apply()
            _tokenFlow.value = value // уведомляем, что токен изменился
        }
}