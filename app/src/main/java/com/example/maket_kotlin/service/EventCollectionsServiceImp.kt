package com.example.maket_kotlin.service

import android.util.Log
import com.example.maket_kotlin.data.dto.EventCollectionDto
import com.example.maket_kotlin.network.BackendClient
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class EventCollectionsServiceImp: EventCollectionsService {
    private val backendClient = BackendClient()

    override fun getCollections(): Flow<List<EventCollectionDto>> = callbackFlow {
        val job = launch {
            try {
                val data = backendClient.getCollections()
                Log.d("CollectionsService", "Collections received: $data")
                trySend(data).isSuccess
            } catch (e: Exception) {
                Log.e("CollectionsService", "Error fetching collections: $e")
                trySend(emptyList()).isSuccess
            } finally {
                close()
            }
        }
        awaitClose { job.cancel() }
    }
}

