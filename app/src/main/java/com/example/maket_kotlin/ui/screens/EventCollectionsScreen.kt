package com.example.maket_kotlin.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.maket_kotlin.ui.components.EventCollections
import com.example.maket_kotlin.viewmodel.EventCollectionsViewModel

@Composable
fun EventCollectionsScreen(viewModel: EventCollectionsViewModel = viewModel()) {
    val collectionsState = viewModel.collectionsState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadCollections()
    }

    if (collectionsState.value.isNotEmpty()) {
        EventCollections(collections = collectionsState.value)
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}