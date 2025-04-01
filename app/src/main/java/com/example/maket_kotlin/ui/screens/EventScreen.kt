package com.example.maket_kotlin.ui.screens

import androidx.compose.runtime.Composable
import com.example.maket_kotlin.ui.components.BottomBar
import com.example.maket_kotlin.ui.components.EventCard

@Composable
fun EventScreen() {
    EventCard(
        imageRes = null,
        eventName = "Мероприятие",
        onLikeClick = {},
        onDislikeClick = {}
    )
    BottomBar()
}