package com.example.maket_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.maket_kotlin.data.dto.TokenRepository
import com.example.maket_kotlin.ui.navigation.AppNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TokenRepository.init(applicationContext)
        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                AppNavHost()
            }
        }
    }
}