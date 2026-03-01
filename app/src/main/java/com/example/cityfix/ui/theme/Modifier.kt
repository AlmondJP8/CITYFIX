package com.example.cityfix.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

//Background color for all layout
fun Modifier.MainBG(): Modifier = this
    .fillMaxSize()
    .background(color = Color(0xFFEEEBEB))






// This is your reusable "Header" style
fun Modifier.headerContainer(): Modifier = this
    .fillMaxWidth()
    .heightIn(min = 75.dp)
    .background(
        color = Color.White,
        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
    )

//Header text and buttons
fun Modifier.headertext(): Modifier = this
    .clickable {  }
    .padding(4.dp)
    .clip(CircleShape)
    .background(Color(0xff0EA5E9))
    .padding(horizontal = 12.dp, vertical = 6.dp)

fun Modifier.Logo(): Modifier = this
    .size(80.dp)
    .padding(10.dp)
    .clip(CircleShape)
//


//content info
fun Modifier.contentBox(): Modifier = this
    .fillMaxWidth()
    .heightIn(min = 150.dp)
    .padding( start = 10.dp, top = 20.dp, end = 10.dp)
    .background(Color(0xff0EA5E9), shape = RoundedCornerShape(10.dp))
    .padding(10.dp)

fun Modifier.tutorialBox(): Modifier = this
    .widthIn(min = 250.dp)
    .heightIn(min = 200.dp)
    .padding( start = 10.dp, top = 20.dp, end = 10.dp)
    .shadow(
        elevation = 10.dp,
        shape = RoundedCornerShape(12.dp),
        clip = false
    )
    .background(Color(0xff0EA5E9), shape = RoundedCornerShape(10.dp))

fun Modifier.contentText(): Modifier = this
    .fillMaxWidth()
    .padding(10.dp)

fun Modifier.Grid(): Modifier = this
    .fillMaxSize()
    .background(Color.White, shape = RoundedCornerShape(12.dp))
    .border(3.dp, Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
