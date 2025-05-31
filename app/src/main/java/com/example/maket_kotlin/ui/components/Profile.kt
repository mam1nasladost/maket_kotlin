package com.example.maket_kotlin.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.maket_kotlin.data.dto.TokenRepository
import com.example.maket_kotlin.data.dto.EventShortDto
import com.example.maket_kotlin.data.dto.EventStatusDto

@Composable
fun Profile(
    name: String,
    surname: String,
    groupName: String,
    events: List<EventStatusDto>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(modifier = Modifier.padding(16.dp), text = "$name $surname", fontSize = 48.sp, fontWeight = FontWeight.Bold)
        Text(modifier = Modifier.padding(16.dp), text = "Группа: $groupName", fontSize = 24.sp, color = Color.DarkGray)
        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(64.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Cписок мероприятий", fontSize = 24.sp, textAlign = TextAlign.Center)
        }

        if (events.isEmpty()) {
            Text(
                text = "Нет мероприятий",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                fontSize = 18.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        } else {
            LazyColumn {
                items(events) { userEvent ->
                    val event = userEvent.event
                    val status = userEvent.status

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .border(1.dp, Color.Gray)
                            .padding(16.dp)
                    ) {
                        Text(text = event.title, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                        Text(text = "Статус: $status", fontSize = 16.sp, color = Color.Gray)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(132.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextButton(
                onClick = {
                    TokenRepository.token = null
                },
            ) {
                Text(
                    text = "Выйти из аккаунта?",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfile() {
    Profile(name = "Iman", surname = "Khayauri", groupName = "IU6-32B", events = emptyList())
}