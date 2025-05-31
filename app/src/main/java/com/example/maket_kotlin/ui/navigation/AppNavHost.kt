package com.example.maket_kotlin.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.maket_kotlin.ui.screens.*
import com.example.maket_kotlin.ui.screens.EventScreen
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import com.example.maket_kotlin.data.dto.TokenRepository
import com.example.maket_kotlin.ui.components.BottomBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    val token = TokenRepository.tokenFlow.collectAsState().value

    LaunchedEffect(token) {
        if (token == null) {
            navController.navigate("auth") {
                popUpTo(0) { inclusive = true }
            }
        } else {
            navController.navigate("home") {
                popUpTo(0) { inclusive = true }
            }
        }
    }


    Scaffold(
        bottomBar = {
            if (currentRoute != "auth" && currentRoute != "register") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .clip(RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp))
                        .background(color = Color.White)
                ){
                    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp, top = 16.dp)) {
                        BottomBar(navController)
                    }
                }
            }
        },
    ) {
        NavHost(
            navController = navController,
            startDestination = if (token == null) "auth" else "home",
            enterTransition = { fadeIn(animationSpec = tween(500)) },
            exitTransition = { fadeOut(animationSpec = tween(500)) },
            popEnterTransition = { fadeIn(animationSpec = tween(500)) },
            popExitTransition = { fadeOut(animationSpec = tween(500)) }
        ) {
            composable("auth") {
                LoginScreen(
                    onLoginClick = {
                        navController.navigate("home") {
                            popUpTo("auth") { inclusive = true }
                        }
                    },
                    onRegisterClick = {
                        navController.navigate("register")
                    }
                )
            }

            composable("register") {
                RegistrationScreen(
                    navController = navController,
                    onLoginClick = {
                        navController.navigate("auth") {
                            popUpTo("register") { inclusive = true }
                        }
                    }
                )
            }

            composable("compilation") {
                EventCollectionsScreen()
            }

            composable("home") {
                EventScreen()
            }

            composable("profile") {
                ProfileScreen()
            }
        }
    }
}