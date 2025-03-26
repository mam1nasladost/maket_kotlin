package com.example.maket_kotlin

import androidx.compose.ui.Alignment
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

//нужно переписать все по SOLID, используя viewmodel прописать получение и отправку данных,
//расписать экраны по отдельным компоузабл, еще и как то сделать чтобы ботомбар всегда отображался на каждом экране
//еще и анимацию проявления карточки надо пофиксить чтобы была красивая тень


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EventCard(
                imageRes = null,
                eventName = "Мероприятие",
                onLikeClick = {},
                onDislikeClick = {}
            )
            BottomBar()
        }
    }
}

data class Event( //дтошка для хранения данных о эвенте
    val id: Int,
    val imageRes: Int?,
    val name: String
)

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
    suspend fun sendLike(eventId: Int):  HttpResponse{
        return client.post("$baseUrl/like?eventId=$eventId")
    }
    //получение событий
    suspend fun getEvents(): List<Event> {
        return client.get("$baseUrl/events").body()
    }

}

//@Serializable
//data class EventLikeRequest(val eventId: Int)
//@Serializable
//data class BackendResponse(val status: String, val message: String)

@Composable
fun EventCard(      //карточка
    imageRes: Int?,
    eventName: String,
    onLikeClick: () -> Unit,
    onDislikeClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val moveX = remember { Animatable(0f) }
    val rotation = remember { Animatable(0f) }
    val transparency = remember { Animatable(1f) }
    val size = remember { Animatable(1f) }

    fun animateSwipe(toRight: Boolean, onEnd: () -> Unit) {     //метод анимации свайпа
        scope.launch {
            val direction = if (toRight) 1f else -1f
            launch { moveX.animateTo(targetValue = direction * 1500f, animationSpec = tween(500)) }
            launch { rotation.animateTo(targetValue = direction * 30f, animationSpec = tween(500)) }
            launch { transparency.animateTo(targetValue = 0f, animationSpec = tween(500)) }

            delay(500)

            moveX.snapTo(0f)
            rotation.snapTo(0f)
            transparency.snapTo(0f)
            size.snapTo(0.8f)

            onEnd()
            launch { transparency.animateTo(1f, animationSpec = tween(400)) }
            launch { size.animateTo(1f, animationSpec = tween(400)) }
        }
    }

    val swipableState = rememberDraggableState { delta ->   //слушатель жеста свайпа
        scope.launch {
            moveX.snapTo(moveX.value + delta)
            rotation.snapTo(moveX.value/40f)
        }
    }

    Card(
        modifier = Modifier
            .padding(32.dp)
            .padding(bottom = 88.dp, top = 32.dp)
            .fillMaxSize()
            .graphicsLayer(
                translationX = moveX.value,
                rotationZ = rotation.value,
                alpha = transparency.value,
                scaleX = size.value,
                scaleY = size.value
            )
            .draggable(
                state = swipableState,
                orientation = Orientation.Horizontal,
                onDragStopped = {
                    if (moveX.value > 300f) {
                        animateSwipe(true, onLikeClick)
                    } else if (moveX.value < -300f) {
                        animateSwipe(false, onDislikeClick)
                    } else {
                        scope.launch {
                            moveX.animateTo(0f, tween(300))
                            rotation.animateTo(0f, tween(300))
                        }
                    }
                }
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(8.dp) //анимация все еще сломанная нужно фиксить, оно просто красивое

    ) {
        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            if (imageRes != null) {
                Image(              //кажется нужно использовать asyncimage для отображения фотографии по ссылке
                    painter = painterResource(id = imageRes),
                    contentDescription = eventName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(620.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(                 //затычка для картинки
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(620.dp)
                        .background(Color.LightGray)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Заглушка изображения", color = Color.DarkGray)
                }
            }

            Text(
                text = eventName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {animateSwipe(false, onDislikeClick) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Дизлайк")
                }
                Button(
                    onClick = {animateSwipe(true, onLikeClick) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                ) {
                    Text("Лайк")
                }
            }
        }
    }
}

@Composable
fun BottomBar() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .offset(y = 810.dp)
            .padding(horizontal = 18.dp)
            .shadow(8.dp, shape = RoundedCornerShape(16.dp))
            .background(Color.LightGray, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        Alignment.CenterVertically,
    ){
    Button(
        onClick = {},
        modifier = Modifier.size(40.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        shape = RoundedCornerShape(18.dp)
        ) { }
    Button(
        onClick = {},
        modifier = Modifier.size(40.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        shape = RoundedCornerShape(18.dp)
    ) { }
    Button(onClick = {},
        modifier = Modifier.size(40.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        shape = RoundedCornerShape(18.dp)
    ) { }
    Button(
        onClick = {},
        modifier = Modifier.size(40.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        shape = RoundedCornerShape(18.dp)
    ) { }
    }
}