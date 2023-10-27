package com.example.songplayer.model

data class SongModel(
    val songname: String,
    val username: String,
    val songduration: String,
    val songurl: String
)

data class GetSongResponse(
    val allSongs: List<SongModel>,
)