package com.example.maket_kotlin.service

import com.example.maket_kotlin.data.dto.EventShortDto
import kotlinx.coroutines.flow.Flow


interface EventService {
    fun getEvents(): Flow<List<EventShortDto>>
    suspend fun sendLike(eventId: Int)
}