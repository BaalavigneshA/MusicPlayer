package com.example.songplayer.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.songplayer.components.BottomNavigationItem
import com.example.songplayer.components.BottomNavigationMenu
import com.example.songplayer.components.SongPlayer
import com.example.songplayer.components.SongTile
import com.example.songplayer.components.TopBar
import com.example.songplayer.helpers.SongHelper
import com.example.songplayer.model.SongModel
import com.example.songplayer.services.AddSongToFirestore
import com.example.songplayer.services.GetAllSongs
import com.example.songplayer.utils.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.immutableListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(navController: NavHostController, username:String) {
    var selectedSong by remember { mutableStateOf<SongModel?>(null) }
    var isSongSelected by remember { mutableStateOf(false) }

    var allSongList by remember {
        mutableStateOf<MutableList<SongModel>?>(null)
    }

    val context = LocalContext.current;
    val store = DataStoreManager(context)

    val username = store.getAccessToken.collectAsState("")

    GetAllSongs() {
        allSongs -> if (allSongs != null) {
        println(allSongs)
        allSongList = allSongs

    } else {
        // Handle the error
    }
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.Center)) {
        TopBar(navController = navController, store = store)

        Text(
            text = "Welcome back, ${username.value}",
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            maxLines = 2,
            modifier = Modifier
                .padding(top = 32.dp, bottom = 24.dp)
                .align(Alignment.CenterHorizontally)
        )

        LazyColumn(modifier = Modifier
            .padding(
                bottom = if (isSongSelected) {
                    48.dp
                } else {
                    4.dp
                }
            )){
            items(allSongList?.size ?: 0) { index ->
                val song = allSongList?.get(index)
                song?.let {
                    SongTile(song , onClick = {
                        isSongSelected = true
                        selectedSong = song
                        SongHelper.stopStream()
                        SongHelper.playStream(song.songurl)
                        println(song.songname)
                    })
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column {
                selectedSong?.let {
                    SongPlayer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent),
                        it
                    )
                }
                BottomNavigationMenu(selectedItem = BottomNavigationItem.HOME, navController = navController, context = context)
            }

        }
    }
}
