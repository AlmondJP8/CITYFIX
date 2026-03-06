package com.example.cityfix.pages.AdminPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cityfix.R
import com.example.cityfix.ui.theme.AppStrings
import com.example.cityfix.ui.theme.ButtonText
import com.example.cityfix.ui.theme.GridConfig
import com.example.cityfix.ui.theme.MainBG
import com.example.cityfix.ui.theme.appName
import com.example.cityfix.ui.theme.gridText
import com.example.cityfix.ui.theme.headerContainer
import com.example.cityfix.ui.theme.headertext
import com.example.cityfix.uiComponents.AdminBottomBar
import com.example.cityfix.uiComponents.AdminHeader

@Composable
fun ReportsPage(navController: NavController?) {
    // 1. Check if we are in the Preview window
    val isPreview = LocalInspectionMode.current
    // 2. Only "listen" to the backstack if we are NOT in the preview
    val currentRoute = if (isPreview) {
        "reports" // Default for preview
    } else {
        navController?.currentBackStackEntryAsState()?.value?.destination?.route
    }


    Scaffold(
        bottomBar = {
            AdminBottomBar(navController = navController, currentRoute = currentRoute)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .MainBG()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AdminHeader()

            Column(
                modifier = Modifier
                    .padding(bottom = innerPadding.calculateBottomPadding()) // Respect the bottom bar
                    .padding(horizontal = 16.dp), // Keep sides clean
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

            }
        }
    }
}

@Preview(showBackground = true, name = "Report Page")
@Composable
fun PreviewReports(){
    ReportsPage(navController = null)
}