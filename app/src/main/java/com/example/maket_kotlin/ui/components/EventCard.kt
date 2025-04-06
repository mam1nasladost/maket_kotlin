package com.example.maket_kotlin.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun EventCard(      //карточка
    imageRes: Int?,
    eventName: String,
    onLikeClick: () -> Unit,
    onDislikeClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val transitionX = remember { Animatable(0f) }
    val rotation = remember { Animatable(0f) }
    val transparency = remember { Animatable(1f) }
    val size = remember { Animatable(1f) }

    fun animateSwipe(toRight: Boolean, onEnd: () -> Unit) {     //метод анимации свайпа
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

            onEnd()
            launch { transparency.animateTo(1f, animationSpec = tween(400)) }
            launch { size.animateTo(1f, animationSpec = tween(400)) }
        }
    }

    val swipableState = rememberDraggableState { delta ->   //слушатель жеста свайпа
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

            if (imageRes != null) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = eventName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.80f) // вес для картинки
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.80f)
                        .background(Color.LightGray)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Заглушка изображения", color = Color.DarkGray)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.15f)
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
            }
        }
    }
}

@Preview(showBackground = true, name = "Превью карточки")
@Composable
fun PreviewEventCard() {
    EventCard(
        imageRes = null,
        eventName = "Пример названия",
        onLikeClick = {},
        onDislikeClick = {}
    )
}

