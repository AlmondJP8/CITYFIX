package com.example.cityfix.uiComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cityfix.R
import com.example.cityfix.ui.theme.appName
import com.example.cityfix.ui.theme.headerContainer

@Composable
fun AdminHeader(
    title: String = "Admin Control Panel", // Default title
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.headerContainer().fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 25.dp)
        ) {
            Text(text = title, style = appName)

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.pic_user_icon),
                contentDescription = "User Profile",
                modifier = Modifier
                    .size(25.dp) // Added size so it's visible
                    .clip(CircleShape)
                    .background(Color(0xFFE8F1F2))
            )
        }
    }
}