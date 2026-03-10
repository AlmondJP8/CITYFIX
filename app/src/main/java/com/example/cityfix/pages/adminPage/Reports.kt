package com.example.cityfix.pages.adminPage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cityfix.R
import com.example.cityfix.ui.theme.GridConfig2
import com.example.cityfix.ui.theme.MainBG
import com.example.cityfix.ui.theme.gridText
import com.example.cityfix.uiComponents.AdminBottomBar
import com.example.cityfix.uiComponents.AdminHeader


data class DashboardItem(
    val title: String,
    val iconRes: Int,
    val route: String,
    val color: Color
)

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

    val dashboardItems = remember {
        listOf(
            DashboardItem("Power", R.drawable.pic_bolt, "power", Color(0xFFFFB300)),
            DashboardItem("Water", R.drawable.pic_water, "water", Color(0xFF0288D1)),
            DashboardItem("Lights", R.drawable.pic_light, "lights", Color(0xFFFBC02D)),
            DashboardItem("Streets", R.drawable.pic_road, "road", Color(0xFF455A64)),
            DashboardItem("Hazards", R.drawable.pic_hazard, "hazards", Color(0xFFE64A19)),
            DashboardItem("Trees", R.drawable.pic_trees, "trees", Color(0xFF388E3C)),
            DashboardItem("Waste", R.drawable.pic_trash, "waste", Color(0xFF8D6E63))
        )
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

                // The Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(GridConfig2.COLUMNS),
                    contentPadding = PaddingValues(top = 10.dp, bottom = 20.dp),
                    verticalArrangement = GridConfig2.VERTICAL_SPACING,
                    horizontalArrangement = GridConfig2.HORIZONTAL_SPACING,
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(dashboardItems) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(110.dp)
                                .clickable { navController?.navigate(item.route) },
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(2.dp))
                        {
                            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize())
                            {
                                Column(horizontalAlignment = Alignment.CenterHorizontally)
                                {
                                    Icon(
                                        painter = painterResource(id = item.iconRes),
                                        contentDescription = item.title,
                                        modifier = Modifier.size(35.dp),
                                        tint = Color.Unspecified
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(text = item.title, style = gridText, fontSize = 14.sp, color = item.color)
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true, name = "Report Page")
@Composable
fun PreviewReports(){
    ReportsPage(navController = null)
}