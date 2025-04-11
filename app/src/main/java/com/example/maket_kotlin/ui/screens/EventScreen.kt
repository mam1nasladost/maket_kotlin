package com.example.maket_kotlin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.maket_kotlin.ui.components.BottomBar
import com.example.maket_kotlin.ui.components.EventCard
import com.example.maket_kotlin.viewmodel.EventViewModel

@Composable
fun EventScreen() {
    val viewModel: EventViewModel = viewModel()
    LaunchedEffect(Unit) {
        viewModel.loadCards()
    }

    val cards by viewModel.cardsState.collectAsState()
    val currentIndex by viewModel.currentIndex.collectAsState()

    if (cards.isNotEmpty()) {
        val currentCard = cards[currentIndex]
        EventCard(
            imageRes = currentCard.imageRes,
            eventName = currentCard.title,
            views = currentCard.views,
            onLikeClick = {
                // Отправляем запрос на лайк в ViewModel
                viewModel.sendLike(currentCard.id)
                viewModel.updateIndex((currentIndex + 1) % cards.size)
            },
            onDislikeClick = {
                viewModel.updateIndex((currentIndex + 1) % cards.size)
            }
        )
    }
    BottomBar()
}