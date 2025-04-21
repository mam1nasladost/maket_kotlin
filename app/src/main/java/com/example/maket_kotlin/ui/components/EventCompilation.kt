package com.example.maket_kotlin.ui.components

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
import coil.compose.AsyncImage
import com.example.maket_kotlin.EventDetails
import com.example.maket_kotlin.data.dto.EventCollectionDto
import com.example.maket_kotlin.data.dto.EventShortDto

@Composable
fun EventCollections(
    collections: List<EventCollectionDto>
) {
    var selectedEvent by remember { mutableStateOf<EventShortDto?>(null) }

    Box(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
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
        selectedEvent?.let { event ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(onClick = { selectedEvent = null })
            ) {
                EventDetails(event = event, onClose = { selectedEvent = null })
            }
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
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        AsyncImage(
            model = event.imageUrl,
            contentDescription = event.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}
