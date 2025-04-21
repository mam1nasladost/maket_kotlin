package com.example.maket_kotlin.service

import com.example.maket_kotlin.data.dto.EventCollectionDto
import kotlinx.coroutines.flow.Flow

interface EventCollectionsService {
    fun getCollections(): Flow<List<EventCollectionDto>>
}