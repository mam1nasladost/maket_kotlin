package com.example.maket_kotlin.data.dto

import com.google.gson.annotations.SerializedName

data class EventCollectionsDto(
    @SerializedName("collections")
    val collections: List<EventShortDto>,
    @SerializedName("title")
    val title: String,
    @SerializedName("id")
    val id: Int,
)
