package com.example.maket_kotlin.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class eventshortdto(
    val id: Int,
    val imageRes: Int?,
    val name: String
)
