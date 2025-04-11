package com.example.maket_kotlin.network

import com.example.maket_kotlin.data.dto.eventshortdto
import io.ktor.client.HttpClient
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

    val header = "X-что то там" //хедер для мобайл запросов

    //отправка лайка
    private val baseUrl = "http://localhost:8080"
    suspend fun sendLike(eventId: Int): HttpResponse {

        return client.post("$baseUrl/like?eventId=$eventId")
    }
    //получение событий
    suspend fun getEvents(): List<eventshortdto> {
        return client.get("$baseUrl/events").body()
    }

    suspend fun register () : HttpResponse {
        //client.post("$baseUrl/registration).body()"
        return TODO("Provide the return value")
    }

}