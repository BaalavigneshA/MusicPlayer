package com.example.songplayer.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.songplayer.screens.AuthScreen
import com.example.songplayer.screens.UploadSong
import com.example.songplayer.screens.WelcomeScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "login",
    username:String,
) {


    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("login") {
            AuthScreen(
                navController = navController,
                username
            )
        }


        composable("home") {
            WelcomeScreen(navController = navController,username)
        }

        composable("upload") {
            UploadSong(navController = navController, username)
        }


    }
}