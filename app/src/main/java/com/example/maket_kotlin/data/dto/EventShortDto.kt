package com.example.maket_kotlin.data.dto

import com.google.gson.annotations.SerializedName

data class EventShortDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("views")
    val views: Int,
    @SerializedName("eventDate")
    val date: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("annotation")
    val description: String,
    @SerializedName("imageDescription")
    val imageDescription: String?
)

