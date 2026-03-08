package com.example.cityfix.uiComponents

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatusBadge(status: String) {
    val (color, label) = when (status) {
        "Pending" -> Color(0xFFFFB74D) to "PENDING"
        "Ongoing" -> Color(0xFF1976D2) to "ONGOING"
        "Resolved" -> Color(0xFF81C784) to "FIXED"
        else -> Color.Gray to "UNKNOWN"
    }

    Surface(
        color = color.copy(alpha = 0.2f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = TextStyle(color = color, fontWeight = FontWeight.Bold, fontSize = 10.sp)
        )
    }
}