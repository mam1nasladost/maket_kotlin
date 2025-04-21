package com.example.maket_kotlin.ui.components

import android.R.attr.fontStyle
import android.R.attr.fontWeight
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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

@Composable
fun Profile(
    name: String,
    surname: String,
    groupName: String,
    photoUrl: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Базовая информация
        AsyncImage(
            model = photoUrl,
            contentDescription = "Фото участника",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(280.dp)
                .border(2.dp, Color.Gray)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "$name $surname", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = groupName, fontSize = 16.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Скоро здесь будет список понравившихся мероприятий!", fontSize = 24.sp, textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(264.dp))

        TextButton(onClick = {TokenRepository.token = null}, ) { Text(text = "Выйти из аккаунта?", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold) }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfile() {
    Profile(name = "Iman", surname = "Khayauri", groupName = "IU6-32B", photoUrl = "https://sun9-61.userapi.com/impg/VPDeoPjDGyRYkOhhdDa8eE06ugivsIa7G8U2nw/-KdqagtyIBQ.jpg?size=622x622&quality=95&sign=96ce7d5fcf3d72b2e6aa66321b650b18&type=album")
}