package com.example.maket_kotlin

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.maket_kotlin.data.dto.EventShortDto
import kotlinx.coroutines.launch

@Composable
fun EventDetails(
    event: EventShortDto,
    onClose: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val transparency = remember { Animatable(1f) }
    val scale = remember { Animatable(1f) }

    fun animateDisappear() {
        scope.launch {
            launch {
                transparency.animateTo(
                    targetValue = 0f,
                    animationSpec = spring(
                        dampingRatio = 0.5f,
                        stiffness = 500f
                    )
                )
            }
            launch {
                scale.animateTo(
                    targetValue = 0.8f,
                    animationSpec = spring(
                        dampingRatio = 0.6f,
                        stiffness = 400f
                    )
                )
            }.join()
            onClose()
        }
    }

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp)
            .graphicsLayer {
                alpha = transparency.value
                scaleX = scale.value
                scaleY = scale.value
            },
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = event.imageUrl,
                contentDescription = event.description,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .weight(1f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = event.description,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .weight(1f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 8.dp) // Добавили отступ снизу для стрелки
                ) {
                    Text(
                        text = "Дата: ${event.date}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = ::animateDisappear,
                        modifier = Modifier.size(36.dp) // Фиксированный размер для кнопки
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Закрыть",
                            modifier = Modifier.size(24.dp) // Размер иконки
                        )
                    }
                }
            }
        }
    }
}
