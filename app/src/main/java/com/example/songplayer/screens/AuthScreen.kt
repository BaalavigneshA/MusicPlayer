package com.example.songplayer.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.songplayer.utils.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.AccessController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(navController: NavHostController, username:String) {

    var userInput by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current;
    val store = DataStoreManager(context = context)

    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        Text(
            text = "Welcome to SongShare",
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            maxLines = 2,
            modifier = Modifier
                .padding(top = 32.dp, bottom = 24.dp)
                .align(Alignment.CenterHorizontally)
        )

        TextField(
            value = userInput,
            modifier = Modifier.padding(12.dp),
            onValueChange = { userInput = it },
            placeholder = { Text(text = "Enter your name") })
        Button(onClick = {
            CoroutineScope(Dispatchers.Main).launch {
                store.saveToken(userInput)
                navController.navigate("home")
            }
        }) {
            Text(text = "Set Username")
        }
    }

}