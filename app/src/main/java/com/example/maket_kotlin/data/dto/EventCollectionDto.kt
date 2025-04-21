package com.example.maket_kotlin.data.dto

import com.google.gson.annotations.SerializedName

data class EventCollectionDto(
    @SerializedName("events")
    val events: List<EventShortDto>,
    @SerializedName("title")
    val title: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("pinned")
    val pinned: Boolean
)
