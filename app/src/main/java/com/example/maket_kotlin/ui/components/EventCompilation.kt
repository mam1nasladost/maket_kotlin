package com.example.maket_kotlin.ui.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import coil.compose.AsyncImage
import com.example.maket_kotlin.data.dto.EventCollectionDto
import com.example.maket_kotlin.data.dto.EventShortDto

@Composable
fun EventCollections(
    collections: List<EventCollectionDto>
) {
    var selectedEvent by remember { mutableStateOf<EventShortDto?>(null) }
    val transition = updateTransition(targetState = selectedEvent != null, label = "detailsTransition")

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

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(all = 16.dp)) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(collections) { collection ->
                Text(
                    text = collection.title,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                )
                LazyRow(modifier = Modifier.padding(vertical = 8.dp)) {
                    items(collection.events) { event ->
                        EventCardFromCollections(event = event) {
                            selectedEvent = event
                        }
                    }
                }
            }
        }

    }

    if (selectedEvent != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = backgroundAlpha))
        )
    }
    selectedEvent?.let { event ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = detailsScale
                    scaleY = detailsScale
                }
        ) {
            EventDetails(event = event, onClose = { selectedEvent = null })
        }
    }
}

@Composable
fun EventCardFromCollections(event: EventShortDto, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .size(160.dp)
            .clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = event.imageUrl,
            contentDescription = event.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}
