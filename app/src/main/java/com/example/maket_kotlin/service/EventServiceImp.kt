package com.example.maket_kotlin.service

import com.example.maket_kotlin.data.dto.eventshortdto
import com.example.maket_kotlin.network.BackendClient
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class EventServiceImp: EventService {

    private val backendClient = BackendClient()

    override fun getEvents(): Flow<List<eventshortdto>> =
        callbackFlow {
            trySendBlocking(
                backendClient.getEvents()
            )
            awaitClose()
        }
    override suspend fun sendLike(eventId: Int) {
        try {
            val response = backendClient.sendLike(eventId)
            if (response.status.value == 200) {
                println("Successfully liked event with ID: $eventId")
            } else {
                println("Failed to like event with ID: $eventId, status: ${response.status}")
            }
        } catch (e: Exception) {
            println("Error liking event with ID: $eventId. Error: ${e.message}")
        }
    }
}
