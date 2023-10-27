package com.example.songplayer.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.songplayer.model.SongModel

@Composable
fun SongTile(song: SongModel?, onClick: () -> Unit) {

    song?.let {
        Card(shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable { onClick() }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(4.dp)
                    .height(56.dp)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier
                    .weight(0.9f)
                    .padding(end = 24.dp)) {
                    Text(
                        text = song.songname,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp

                    )
                    Text(
                        text = song.username,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        fontSize = 16.sp,
                    )
                }

                Text(
                    text = song.songduration, modifier = Modifier
                        .weight(0.15f)
                )
            }
        }
    }
}