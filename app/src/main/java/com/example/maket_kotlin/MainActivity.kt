package com.example.maket_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.maket_kotlin.data.dto.TokenRepository
import com.example.maket_kotlin.ui.components.EventCard
import com.example.maket_kotlin.ui.navigation.AppNavHost

//нужно переписать все по SOLID, используя viewmodel прописать получение и отправку данных,
//расписать экраны по отдельным компоузабл, настроить перемещение по ним через NavHost,
//еще и как то сделать чтобы ботомбар всегда отображался на каждом экране,
//еще и анимацию проявления карточки надо пофиксить чтобы была красивая тень

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TokenRepository.init(applicationContext)
        setContent {

            // Включаем навигацию в нашем приложении
            Surface(color = MaterialTheme.colorScheme.background) {
                AppNavHost()  // Подключаем наш NavHostЪ
            }
        }
    }
}