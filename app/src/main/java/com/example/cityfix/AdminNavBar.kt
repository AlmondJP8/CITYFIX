package com.example.cityfix

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AdminBottomBar(navController: NavController, currentRoute: String?) {
    // Column wraps the Divider and the Bar to keep them together
    Column {
        // A thin line to separate the bar from the content above it
        HorizontalDivider(
            thickness = 0.5.dp,
            color = Color.LightGray.copy(alpha = 0.5f)
        )

        NavigationBar(
            modifier = Modifier.height(90.dp), // Slimmer, professional height
            containerColor = Color.White,       // Forces white background
            tonalElevation = 0.dp               // Removes the Material 3 grey/purple tint
        ) {
            // DASHBOARD ITEM
            NavigationBarItem(
                selected = currentRoute == "admin",
                onClick = {
                    if (currentRoute != "admin") {
                        navController.navigate("admin") {
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

                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent, // Removes the "pill" background
                    selectedIconColor = Color(0xFF1976D2), // Professional Blue
                    selectedTextColor = Color(0xFF1976D2),
                    unselectedIconColor = Color.Black,
                    unselectedTextColor = Color.Black
                )
            )

            // USERS ITEM
            NavigationBarItem(
                selected = currentRoute == "users",
                onClick = {
                    // This will work once you create a "users" screen
                    if (currentRoute != "users") {
                        navController.navigate("users")
                    }
                },
                label = { Text("Users", fontSize = 14.sp) },
                icon = { Icon(painterResource(id = R.drawable.pic_ticket),
                    contentDescription = "Users",
                    modifier = Modifier.size(25.dp))},

                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = Color(0xFF1976D2),
                    selectedTextColor = Color(0xFF1976D2),
                    unselectedIconColor = Color.Black,
                    unselectedTextColor = Color.Black
                )
            )

            // EXIT ITEM
            NavigationBarItem(
                selected = false,
                onClick = {
                    // Clears the admin stack and goes back to the home/greeting screen
                    navController.navigate("home") {
                        popUpTo("admin") { inclusive = true }
                    }
                },
                label = { Text("Exit", fontSize = 14.sp)},
                icon = { Icon(painterResource(id = R.drawable.pic_cog1),
                    contentDescription = "Exit",
                    modifier = Modifier.size(25.dp))},

                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = Color.Red, // Make exit stand out slightly
                    selectedTextColor = Color.Red,
                    unselectedIconColor = Color.Black,
                    unselectedTextColor = Color.Black
                )
            )
        }
    }
}