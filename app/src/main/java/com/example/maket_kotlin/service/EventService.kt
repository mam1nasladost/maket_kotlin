package com.example.maket_kotlin.service

import com.example.maket_kotlin.data.dto.eventshortdto
import kotlinx.coroutines.flow.Flow


interface EventService {
    fun getEvents(): Flow<List<eventshortdto>>
    suspend fun sendLike(eventId: Int)
}