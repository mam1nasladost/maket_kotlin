package com.example.maket_kotlin.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.graphics.graphicsLayer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import com.example.maket_kotlin.data.dto.EventShortDto
import com.example.maket_kotlin.ui.components.EventCard
import com.example.maket_kotlin.ui.components.EventDetails
import com.example.maket_kotlin.viewmodel.EventViewModel
import androidx.compose.ui.graphics.Color

@Composable
fun EventScreen() {
    val viewModel: EventViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.loadCards()
    }
    val cards by viewModel.cardsState.collectAsState()
    val currentIndex by viewModel.currentIndex.collectAsState()

    var selectedEvent by remember { mutableStateOf<EventShortDto?>(null) }
    val transition = updateTransition(targetState = selectedEvent != null, label = "detailsTransition")

    val cardScale by transition.animateFloat(
        transitionSpec = { tween(300) },
    ) { showingDetails -> if (showingDetails) 0.8f else 1f }

    val detailsScale by transition.animateFloat(
        transitionSpec = {
            if (targetState) {
                spring(dampingRatio = 0.7f, stiffness = 500f)
            } else {
                tween(300)
            }
        },
    ) { showingDetails -> if (showingDetails) 1f else 0.8f }

    val backgroundAlpha by transition.animateFloat(
        transitionSpec = { tween(300) }
    ) { showingDetails -> if (showingDetails) 0.3f else 0f }

    Box(modifier = Modifier.fillMaxSize()) {
        if (cards.isNotEmpty()) {
            val currentCard = cards[currentIndex]
            Box(modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = cardScale
                    scaleY = cardScale
                }
            ) {
                EventCard(
                    imageRes = currentCard.imageUrl,
                    eventName = currentCard.title,
                    imageDescription = currentCard.imageDescription,
                    onLikeClick = {
                        viewModel.sendLike(currentCard.id)
                        viewModel.updateIndex((currentIndex + 1) % cards.size)
                    },
                    onDislikeClick = {
                        viewModel.updateIndex((currentIndex + 1) % cards.size)
                    },
                    onDetailsClick = {
                        selectedEvent = currentCard
                    },
                )
            }
        }
        else
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

        selectedEvent?.let { event ->
            Box(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = backgroundAlpha))
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            scaleX = detailsScale
                            scaleY = detailsScale
                        }
                ) {
                    EventDetails(
                        event = event,
                        onClose = { selectedEvent = null },
                    )
                }
            }
        }
    }
}