package com.example.maket_kotlin.data.dto

import android.content.Context
import android.content.SharedPreferences

object TokenRepository {
    private lateinit var sharedPreferences: SharedPreferences

    // Инициализация TokenRepository
    fun init(context: Context) {
        if (!::sharedPreferences.isInitialized) {
            sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        }
    }

    var token: String?
        get() = sharedPreferences.getString("TOKEN", null)
        set(value) {
            sharedPreferences.edit().putString("TOKEN", value).apply()
        }
}