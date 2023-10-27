package com.example.songplayer.services

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import com.example.songplayer.model.SongModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Objects
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

fun AddSongToFirestore(username:String, song:String, duration:String, url:String) {

    val db = Firebase.firestore;
    // Create a new user with a first and last name
    val songinfo = hashMapOf(
        "username" to username,
        "songname" to song,
        "songduration" to duration,
        "songurl" to url
    )

// Add a new document with a generated ID
    db.collection("users")
        .add(songinfo)
        .addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w(TAG, "Error adding document", e)
        }

}

fun GetAllSongs(onComplete: (MutableList<SongModel>?) -> Unit) {
    val db = Firebase.firestore;

    val allSongList = mutableListOf<SongModel>()
    db.collection("users")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                Log.d(TAG, "${document.id} => ${document.data}")

                val songData = document.data
                val oneSong = SongModel(
                    songname = songData["songname"] as String,
                    username = songData["username"] as String,
                    songduration = songData["songduration"] as String,
                    songurl = songData["songurl"] as String
                )

                allSongList.add(oneSong)
            }
            onComplete(allSongList)

        }
        .addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents.", exception)
        }
}

fun AddSongToFirebaseStorage(
    storage: FirebaseStorage,
    uri: Uri,
    onComplete: (String?) -> Unit
) {
    val storageRef = storage.reference
    val fileName = "audio_${System.currentTimeMillis()}.mp3"
    val audioRef = storageRef.child(fileName)

    val uploadTask = audioRef.putFile(uri)

    uploadTask.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            audioRef.downloadUrl.addOnSuccessListener { downloadUri ->
                onComplete(downloadUri.toString())
            }
        } else {
            onComplete(null) // Handle the upload error
        }
    }
}