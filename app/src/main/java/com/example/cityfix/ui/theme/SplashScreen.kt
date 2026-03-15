package com.example.cityfix.ui.theme

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cityfix.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // 1. Setup the scale state
    val scale = remember { Animatable(0f) }

    // 2. Run the animation when the screen opens
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1.0f,
            animationSpec = tween(
                durationMillis = 400,
                easing = { OvershootInterpolator(6f).getInterpolation(it) } // This creates the "pop"
            )
        )
        delay(1200L) // Wait 2 seconds so the user can see it
        navController.navigate("start") { // Move to your main page
            popUpTo("splash_screen") { inclusive = true }
        }
    }

    // 3. The UI Layout
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.pic_logo), // Your logo
                contentDescription = "Logo",
                modifier = Modifier.size(120.dp).scale(scale.value)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "CITYFIX",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1976D2),
                modifier = Modifier.scale(scale.value) // The name also pops up
            )
        }
    }
}