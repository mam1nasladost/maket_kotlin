package com.example.maket_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.example.maket_kotlin.ui.components.BottomBar
import com.example.maket_kotlin.ui.screens.EventScreen
import com.example.maket_kotlin.ui.screens.RegistrationScreen

//нужно переписать все по SOLID, используя viewmodel прописать получение и отправку данных,
//расписать экраны по отдельным компоузабл, настроить перемещение по ним через NavHost,
//еще и как то сделать чтобы ботомбар всегда отображался на каждом экране,
//еще и анимацию проявления карточки надо пофиксить чтобы была красивая тень


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EventScreen()
        }
    }
}
