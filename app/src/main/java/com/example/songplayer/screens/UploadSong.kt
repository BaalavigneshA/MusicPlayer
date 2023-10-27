package com.example.songplayer.screens

import android.content.Context
import android.content.Intent
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import com.example.songplayer.components.BottomNavigationItem
import com.example.songplayer.components.BottomNavigationMenu
import com.example.songplayer.components.TopBar
import com.example.songplayer.services.AddSongToFirebaseStorage
import com.example.songplayer.services.AddSongToFirestore
import com.example.songplayer.utils.DataStoreManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun UploadSong(navController: NavHostController, username: String) {
    val storage = Firebase.storage        // Add a button to select an audio file from the device
    val context = LocalContext.current;
    val store = DataStoreManager(context)


    var selectedAudioUri by remember { mutableStateOf<Uri?>(null) }

    var songname by remember { mutableStateOf<String>("") }
    var songduration by remember { mutableStateOf<String>("") }


    var downloadUriMe by remember { mutableStateOf("") }

    // Create an ActivityResultLauncher for selecting audio files
    val audioPickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                val cursor = context.contentResolver.query(it, null, null, null, null)
                val displayNameIndex = cursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME)

                if (cursor != null && cursor.moveToFirst()) {
                    val fullName = cursor.getString(displayNameIndex ?: -1)

                    // Extract the file name without the extension
                    val fileNameWithoutExtension = fullName.substringBeforeLast(".")
                    songname = fileNameWithoutExtension
                }

                cursor?.close()
            }

            if (uri != null) {
                selectedAudioUri = uri
                // Handle the selected audio URI here
                // You can upload the selected audio file to Firebase Storage or perform any other actions.

                // Retrieve audio file information using MediaMetadataRetriever
                val metadataRetriever = MediaMetadataRetriever()
                metadataRetriever.setDataSource(context, uri)

                // Retrieve the audio file's duration in milliseconds
                val songdurationmillis =
                    metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLong()

                songdurationmillis?. let {
                    val durationInSeconds = songdurationmillis / 1000
                    val minutes = durationInSeconds / 60
                    var seconds = durationInSeconds % 60

                    songduration = if(seconds < 10) {
                        "$minutes:0$seconds"
                    } else {
                        "$minutes:$seconds"
                    }
                    println("Duration: $songduration")
                }
                // Close the MediaMetadataRetriever to release resources
                metadataRetriever.release()
            }
        }


    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        TopBar(navController = navController, store = store)
        Text(text = "Upload Song", fontSize = 24.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(24.dp))

        // Add a button to select an audio file from the device
        Button(
            onClick = {
                // Launch the file picker to select an audio file
                audioPickerLauncher.launch("audio/*")
            }
        ) {
            Text(text = "Select Audio File")
        }

        // Display the selected audio file's name, if available
        selectedAudioUri?.let { uri ->
            Column (modifier = Modifier.height(56.dp), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Center){
                Text(text = "Song Name: $songname", overflow = TextOverflow.Ellipsis, maxLines = 2)
                Text(text = "Song Duration: $songduration")
            }


        }

        // Upload the selected audio file to Firebase Storage
        Button(
            onClick = {
                selectedAudioUri?.let { uri ->
                    AddSongToFirebaseStorage(storage, uri) { downloadUrl ->
                        if (downloadUrl != null) {
                            // File uploaded successfully, use downloadUrl
                            println(downloadUrl)
                            Toast.makeText(context, "Song Successfully Uploaded", Toast.LENGTH_SHORT).show()

                            AddSongToFirestore(username, songname, songduration, downloadUrl)
                        } else {
                            // Handle the error
                            Toast.makeText(context, "Song Upload Failed", Toast.LENGTH_SHORT).show()
                        }
                        navController.navigate("home")
                    }
                }
            }
        ) {
            Text(text = "Upload to Firebase Storage")
        }

        downloadUriMe?.let { downloadUrl -> Text(text = downloadUriMe) }


        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            BottomNavigationMenu(
                selectedItem = BottomNavigationItem.UPLOAD,
                navController = navController,
                context = context
            )
        }
    }
}
