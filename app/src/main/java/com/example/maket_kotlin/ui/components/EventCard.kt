package com.example.maket_kotlin.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun EventCard(
    imageRes: String,
    eventName: String,
    views: Int,
    onLikeClick: () -> Unit,
    onDislikeClick: () -> Unit,
    onDetailsClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val transitionX = remember { Animatable(0f) }
    val rotation = remember { Animatable(0f) }
    val transparency = remember { Animatable(1f) }
    val size = remember { Animatable(1f) }

    fun animateSwipe(toRight: Boolean, onEnd: () -> Unit) {
        scope.launch {
            val direction = if (toRight) 1f else -1f
            launch { transitionX.animateTo(targetValue = direction * 1500f, animationSpec = tween(500)) }
            launch { rotation.animateTo(targetValue = direction * 30f, animationSpec = tween(500)) }
            launch { transparency.animateTo(targetValue = 0f, animationSpec = tween(500)) }


            delay(500)

            transitionX.snapTo(0f)
            rotation.snapTo(0f)
            transparency.snapTo(0f)
            size.snapTo(0.8f)

            onEnd() //вызов появления новой карточки
            launch { transparency.animateTo(1f, animationSpec = tween(400)) }
            launch { size.animateTo(1f, animationSpec = tween(400)) }
        }
    }

    val swipableState = rememberDraggableState { delta ->
        scope.launch {
            transitionX.snapTo(transitionX.value + delta)
            rotation.snapTo(transitionX.value/40f)
        }
    }

    Card(
        modifier = Modifier
            .padding(32.dp)
            .padding(bottom = 86.dp, top = 32.dp)
            .fillMaxSize()
            .graphicsLayer(
                translationX = transitionX.value,
                rotationZ = rotation.value,
                alpha = transparency.value,
                scaleX = size.value,
                scaleY = size.value
            )
            .draggable(
                state = swipableState,
                orientation = Orientation.Horizontal,
                onDragStopped = {
                    if (transitionX.value > 300f) {
                        animateSwipe(true, onLikeClick)
                    } else if (transitionX.value < -300f) {
                        animateSwipe(false, onDislikeClick)
                    } else {
                        scope.launch {
                            transitionX.animateTo(0f, tween(300))
                            rotation.animateTo(0f, tween(300))
                        }
                    }
                }
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(8.dp) //анимация все еще сломанная нужно фиксить, оно просто красивое

    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = imageRes,
                contentDescription = eventName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(5f)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = eventName,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.fillMaxWidth()
                )
                IconButton(onClick = onDetailsClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Открыть доп.информациб")}
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End) {
                    Text(
                        text = "Просмотры: $views",
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Thin,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                    )
                }

            }
        }
    }
}
