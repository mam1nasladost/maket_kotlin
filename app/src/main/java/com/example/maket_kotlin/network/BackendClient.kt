package com.example.maket_kotlin.network

import android.util.Log
import com.example.maket_kotlin.data.dto.AuthRequest
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

    private val baseUrl = "http://37.9.4.22:8080"

    suspend fun sendLike(eventId: Int): HttpResponse {
        return client.post("$baseUrl/api/requests?eventId=$eventId")
    }

    suspend fun getEvents(): List<EventShortDto> {
        val token = TokenRepository.token
        Log.e("Authorization", "Bearer $token")

        val response = client.get("$baseUrl/api/events") {
            header("Authorization", "Bearer Bearer $token")
        }

        val eventList = response.body<List<EventShortDto>>()
        Log.d("BackendClient", "Received events: $eventList")

        return eventList
    }

    suspend fun auth(request: AuthRequest): HttpResponse {
        val response = client.post("$baseUrl/api/auth/sign-in") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        if (response.status.isSuccess()) {
            TokenRepository.token = response.bodyAsText() // сохраняем токен
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
