package com.example.songplayer.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.example.songplayer.R

enum class BottomNavigationItem(val icon: Int, val navDestination: String) {
    HOME(R.drawable.home_icon, "home"),
    UPLOAD(R.drawable.upload_icon, "upload"),
}

@Composable
fun BottomNavigationMenu(selectedItem: BottomNavigationItem, navController: NavController, context: Context) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 12.dp)
            .background(Color.LightGray)
    ) {
        for (item in BottomNavigationItem.values()) {
            Image(
                painter = painterResource(id = item.icon),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .padding(8.dp)
                    .height(30.dp)
                    .weight(1f)
                    .clickable {
                        navController.navigate(item.navDestination)
                    },
                colorFilter = if (item == selectedItem) ColorFilter.tint(Color.Black) else ColorFilter.tint(
                    Color.Gray
                )
            )
        }
    }
}