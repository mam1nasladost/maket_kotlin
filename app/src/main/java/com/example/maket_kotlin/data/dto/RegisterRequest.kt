package com.example.maket_kotlin.data.dto

data class RegisterRequest(
    val login: String,
    val name: String,
    val surname: String,
    val groupName: String,
    val password: String
)