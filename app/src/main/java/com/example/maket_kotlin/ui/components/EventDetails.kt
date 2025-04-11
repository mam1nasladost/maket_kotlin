package com.example.maket_kotlin

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.maket_kotlin.data.dto.eventshortdto
import kotlinx.coroutines.launch

@Composable
fun EventDetails(
    event: eventshortdto,
    onClose: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val transparency = remember { Animatable(1f) }
    val size = remember { Animatable(1f) }

    fun AnimateAppear() {
        scope.launch() {
            launch { transparency.animateTo(1f, animationSpec = tween(400)) }
            launch { size.animateTo(1f, animationSpec = tween(400)) }
        }
    }

    fun AnimateDisappear() {
        scope.launch {
            launch { transparency.animateTo(0f, animationSpec = tween(300)) }
            launch { size.animateTo(0.8f, animationSpec = tween(300)) }.join()
            onClose()
        }
    }

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
            .graphicsLayer(
                alpha = transparency.value,
                scaleX = size.value,
                scaleY = size.value
            ),
        shape = RoundedCornerShape(16.dp),
        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = event.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = event.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = event.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Text(
                text = "Дата: ${event.date}",
                style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.weight(1f))

                IconButton(onClick = ::AnimateDisappear) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Закрыть")}
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewEventDetails() {
    val testEvent = eventshortdto(
        id = 1,
        imageRes = R.drawable.studiu,
        title = "Тестовое событие",
        description = "Это описание тестового события для проверки экрана подробностей.",
        date = "6 апреля 2025",
        views = 1332
    )
    EventDetails(testEvent, onClose = {})
}