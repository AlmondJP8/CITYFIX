package com.example.cityfix.uiComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cityfix.R

@Composable
fun AdminBottomBar(navController: NavController?, currentRoute: String?) {
    val itemColors = NavigationBarItemDefaults.colors(
        indicatorColor = Color.Transparent,
        selectedIconColor = Color(0xFF1976D2),
        selectedTextColor = Color(0xFF1976D2),
        unselectedIconColor = Color.Black,
        unselectedTextColor = Color.Black
    )

    // Column wraps the Divider and the Bar to keep them together
    Column {
        // A thin line to separate the bar from the content above it
        HorizontalDivider(
            thickness = 0.5.dp,
            color = Color.LightGray.copy(alpha = 0.5f)
        )

        NavigationBar(
            modifier = Modifier.height(90.dp),
            containerColor = Color.White,       // Forces white background
            tonalElevation = 0.dp               // Removes the Material 3 gray/purple tint
        ) {
            // DASHBOARD ITEM
            NavigationBarItem(
                selected = currentRoute == "admin",
                onClick = {
                    if (currentRoute != "admin") {
                        navController?.navigate("admin") {
                            // Avoid multiple copies of the same screen on the stack
                            popUpTo("admin") { saveState = true }
                            launchSingleTop = true
                        }
                    }
                },
                label = { Text("Dashboard", fontSize = 14.sp) },
                icon = { Icon(painterResource(id = R.drawable.pic_dashboard),
                    contentDescription = "Dashboard",
                    modifier = Modifier.size(25.dp))},
                    colors = itemColors
            )

            // USERS ITEM
            NavigationBarItem(
                    selected = currentRoute == "reports",
            onClick = {
                // This will work once you create a "users" screen
                if (currentRoute != "reports") {
                    navController?.navigate("reports"){
                        popUpTo("reports") { saveState = true}
                        launchSingleTop = true
                    }
                }
            },
            label = { Text("Reports", fontSize = 14.sp) },
            icon = { Icon(painterResource(id = R.drawable.pic_ticket),
                contentDescription = "Reports",
                modifier = Modifier.size(25.dp))},
                colors = itemColors
            )

            // MAP ITEM
            NavigationBarItem(
                selected = currentRoute == "map",
                onClick = {
                    if (currentRoute != "map") {
                        navController?.navigate("map") {
                            popUpTo("map") { saveState = true }
                            launchSingleTop = true
                        }
                    }
                },
                label = { Text("Map", fontSize = 14.sp) },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.pic_maps),
                        contentDescription = "Map",
                        modifier = Modifier.size(25.dp))},
                        colors = itemColors
            )

            // EXIT ITEM
            NavigationBarItem(
                selected = currentRoute == "setting",
                onClick = {
                    // Clears the admin stack and goes back to the home/greeting screen
                    navController?.navigate("setting") {
                        popUpTo("setting") { inclusive = true }
                        launchSingleTop = true
                    }
                },
                label = { Text("Settings", fontSize = 14.sp)},
                icon = { Icon(painterResource(id = R.drawable.pic_cog1),
                    contentDescription = "Setting",
                    modifier = Modifier.size(25.dp))},
                    colors = itemColors
            )
        }
    }
}