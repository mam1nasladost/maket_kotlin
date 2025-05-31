package com.example.maket_kotlin.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
            .padding(top = 64.dp, bottom = 106.dp, start = 20.dp, end = 20.dp)
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

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = event.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = event.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp),
            ) {
                Text(
                    text = "Дата: ${event.date}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                )
                Text(
                    text = "Просмотры: ${event.views}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.Right) {
                IconButton(
                    onClick = ::animateDisappear,
                    modifier = Modifier.size(36.dp).offset(x = (-16).dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Закрыть",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
