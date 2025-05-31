package com.example.maket_kotlin.data.dto
import com.google.gson.annotations.SerializedName

data class EventRequestDto(
    @SerializedName("status")
    val status: String,
    @SerializedName("event")
    val id: Int
)
