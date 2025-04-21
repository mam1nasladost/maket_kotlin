package com.example.maket_kotlin.data.dto

data class Email(val id: Int, val email: String)
data class Phone(val id: Int, val phone: String)

data class UserResponse(
    val name: String,
    val surname: String,
    val groupName: String,
    val emails: List<Email>,
    val phones: List<Phone>
)
