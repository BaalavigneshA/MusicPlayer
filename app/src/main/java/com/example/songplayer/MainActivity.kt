package com.example.songplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.songplayer.ui.theme.SongPlayerTheme
import com.example.songplayer.utils.AppNavHost
import com.example.songplayer.utils.DataStoreManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Initialize DataStoreManager
        dataStoreManager = DataStoreManager(this)

        // Initialize the username variable with a default value (e.g., empty string)
        var username = ""

        // Launch a coroutine to asynchronously fetch the username from DataStore
        lifecycleScope.launch {
            username = dataStoreManager.getAccessToken.first() ?: ""
            updateUIWithUsername(username = username)
            // Update your UI or perform any action based on the username here.
        }

    }


    private fun updateUIWithUsername(username: String) {
        setContent {
            SongPlayerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost(
                        startDestination = if(username.isNotEmpty()) {
                            "home"
                        } else {
                            "login"
                        },
                        username = username,
                    )
                }
            }
        }
    }
}

