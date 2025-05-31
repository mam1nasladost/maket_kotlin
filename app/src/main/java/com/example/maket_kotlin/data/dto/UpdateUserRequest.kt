package com.example.maket_kotlin.data.dto

data class UpdateUserRequest(
    val login: String,
    val password: String,
    val groupName: String
)
