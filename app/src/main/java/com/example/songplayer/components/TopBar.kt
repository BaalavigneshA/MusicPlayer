package com.example.songplayer.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.songplayer.utils.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun TopBar(navController: NavController, store:DataStoreManager) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight().height(80.dp)
            .background(Color.LightGray),
                verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "MyVoice",
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(0.6f).padding(start = 24.dp)
        )
        Button(onClick = {
            CoroutineScope(Dispatchers.Main).launch {
                store.saveToken("")
                navController.navigate("login")
            }
        }, modifier = Modifier.weight(0.3f).padding(end = 24.dp)) {
            Text(text = "Logout")
        }
    }
}