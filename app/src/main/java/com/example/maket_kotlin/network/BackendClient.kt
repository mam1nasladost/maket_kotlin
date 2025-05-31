package com.example.maket_kotlin.network

import android.util.Log
import com.example.maket_kotlin.data.dto.AuthRequest
import com.example.maket_kotlin.data.dto.EventCollectionDto
import com.example.maket_kotlin.data.dto.EventRequestDto
import com.example.maket_kotlin.data.dto.EventShortDto
import com.example.maket_kotlin.data.dto.RegisterRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.serialization.gson.gson
import com.example.maket_kotlin.data.dto.TokenRepository
import com.example.maket_kotlin.data.dto.UpdateUserRequest
import com.example.maket_kotlin.data.dto.UserResponse
import io.ktor.client.request.patch
import io.ktor.http.isSuccess

class BackendClient() {

    val client = HttpClient(CIO) {
        defaultRequest {
            TokenRepository.token?.let {
                if (!url.encodedPath.contains("sign-up") && !url.encodedPath.contains("sign-in")) {
                    header("Authorization", "Bearer Bearer $it")
                }
            }
        }
        install(ContentNegotiation) {
            gson()
        }
    }

    private val baseUrl = "http://10.0.2.2:8080"

    suspend fun sendLike(eventId: Int): HttpResponse {
        return client.post("$baseUrl/api/requests?eventId=$eventId")
    }

    suspend fun getEvents(): List<EventShortDto> {
        val token = TokenRepository.token

        val response = client.get("$baseUrl/api/events") {
            header("Authorization", "Bearer Bearer $token")
        }

        val eventList = response.body<List<EventShortDto>>()
        Log.d("BackendClient", "Received events: $eventList")

        return eventList
    }

    suspend fun getUserInfo(): UserResponse {
        val token = TokenRepository.token

        val response = client.get("$baseUrl/api/users") {
            header("Authorization", "Bearer Bearer $token")
        }

        return response.body()
    }

    suspend fun getUserEventsId(): List<EventRequestDto> {
        val token = TokenRepository.token

        val response = client.get("$baseUrl/api/requests") {
            header("Authorization", "Bearer Bearer $token")
        }

        Log.d("BackendClient", "Received user event IDs: ${response.bodyAsText()}")

        val result = response.body<List<EventRequestDto>>()

        return result

    }

    suspend fun getEventById(id: Int): EventShortDto {
        val token = TokenRepository.token

        val response = client.get("$baseUrl/api/events/$id") {
            header("Authorization", "Bearer Bearer $token")
        }

        return response.body<EventShortDto>()
    }

    suspend fun updateUserInfo(request: UpdateUserRequest): HttpResponse {
        val token = TokenRepository.token

        val response = client.patch("$baseUrl/api/users") {
            header("Authorization", "Bearer Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        return response.body()
    }

    suspend fun getCollections(): List<EventCollectionDto> {
        val token = TokenRepository.token

        val response = client.get("$baseUrl/api/compilations") {
            header("Authorization", "Bearer Bearer $token")
        }

        return response.body()
    }

    suspend fun auth(request: AuthRequest): HttpResponse {
        val response = client.post("$baseUrl/api/auth/sign-in") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        if (response.status.isSuccess()) {
            TokenRepository.token = response.bodyAsText()
        }
        return response
    }

    suspend fun register(request: RegisterRequest): HttpResponse {
        return client.post("$baseUrl/api/auth/sign-up") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }
}
