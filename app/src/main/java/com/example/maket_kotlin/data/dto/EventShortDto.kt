package com.example.maket_kotlin.data.dto

import com.google.gson.annotations.SerializedName

data class EventShortDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("views")
    val views: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("date")
    val date: String
)

