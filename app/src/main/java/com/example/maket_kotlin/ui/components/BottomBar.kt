package com.example.maket_kotlin.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun BottomBar() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .offset(y = 810.dp)
            .padding(horizontal = 18.dp)
            .shadow(8.dp, shape = RoundedCornerShape(16.dp))
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        Alignment.CenterVertically,
    ){
        Button(onClick = {},
            modifier = Modifier.size(44.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
            shape = RoundedCornerShape(18.dp)
        ) { }
        Button(onClick = {},
            modifier = Modifier.size(44.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
            shape = RoundedCornerShape(18.dp)
        ) { }
        Button(onClick = {},
            modifier = Modifier.size(44.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
            shape = RoundedCornerShape(18.dp)
        ) { }
        Button(onClick = {},
            modifier = Modifier.size(44.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
            shape = RoundedCornerShape(18.dp)
        ) { }
    }
}
