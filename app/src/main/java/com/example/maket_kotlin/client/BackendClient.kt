package com.example.maket_kotlin.client

import android.app.usage.UsageEvents.Event
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class BackendClient {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }

    //отправка лайка
    private val baseUrl = "http://localhost:8080"
    suspend fun sendLike(eventId: Int): HttpResponse {
        return client.post("$baseUrl/like?eventId=$eventId")
    }
    //получение событий
    suspend fun getEvents(): List<Event> {
        return client.get("$baseUrl/events").body()
    }

}