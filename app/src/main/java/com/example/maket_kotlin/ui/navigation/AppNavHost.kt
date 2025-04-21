package com.example.maket_kotlin.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.maket_kotlin.ui.screens.*
import com.example.maket_kotlin.EventScreen
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.maket_kotlin.data.dto.TokenRepository
import com.example.maket_kotlin.ui.components.BottomBar

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
                BottomBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if (token == null) "auth" else "home",
            modifier = Modifier.padding(innerPadding),
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