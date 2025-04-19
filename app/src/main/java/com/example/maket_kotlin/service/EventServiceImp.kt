package com.example.maket_kotlin.service

import android.util.Log
import com.example.maket_kotlin.R
import com.example.maket_kotlin.data.dto.EventShortDto
import com.example.maket_kotlin.network.BackendClient
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class EventServiceImp: EventService {

    private val backendClient = BackendClient()
    private var eventsList: List<EventShortDto> = emptyList()


    override fun getEvents(): Flow<List<EventShortDto>> = callbackFlow {
        val job = launch {
            try {
                eventsList = backendClient.getEvents()
                Log.d("EventServiceImp", "Events received: $eventsList")
                trySend(eventsList).isSuccess
            } catch (e: Exception) {
                Log.e("EventServiceImp", "Error fetching events: ${e}")
                trySend(emptyList()).isSuccess
            } finally {
                close() // закрываем flow после отправки
            }
        }
        awaitClose {
            job.cancel()
        }
    }
//        val events = listOf<EventShortDto>(
//        EventShortDto (
//            id = 1,
//            imageRes = R.drawable.studiu,
//            views = 1232,
//            title = "New Year",
//            description = "bla bla bla",
//            date = "12.01.01"
//        ),
//        EventShortDto (
//            id = 1,
//            imageRes = R.drawable.studiu,
//            views = 1232,
//            title = "Blow Year",
//            description = "bla bla bla",
//            date = "12.01.01"
//        ),
//        EventShortDto (
//            id = 1,
//            imageRes = R.drawable.studiu,
//            views = 23112,
//            title = "Few Year",
//            description = "bla bla bla",
//            date = "12.01.01"
//        )
//        )
//        val job = launch {
//            Log.e("EventServiceImp", "Zashli v func")
//            try {
//                trySend(events).isSuccess
//            }
//            catch (e: Exception) {
//                Log.e("EventServiceImp", "Error fetching events: ${e}")
//            }
//            finally {
//                close()
//            }
//        }
//        awaitClose {
//            job.cancel()
//        }

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
