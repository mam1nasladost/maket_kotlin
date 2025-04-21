package com.example.maket_kotlin.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.maket_kotlin.ui.components.Profile
import com.example.maket_kotlin.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = viewModel()) {
    val user = viewModel.user.collectAsState(initial = null).value

    if (user == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Profile(
            name = user.name,
            surname = user.surname,
            groupName = user.groupName,
            photoUrl = "https://sun9-61.userapi.com/impg/pjuzizbgIbI0dpLLEpuWoFwJ8Qadf4GyisoIRg/9B5Czz-wWn4.jpg?size=1024x1024&quality=95&sign=dcf4bbc412cab82536a58d803526ef58&type=album",
        )
    }
}