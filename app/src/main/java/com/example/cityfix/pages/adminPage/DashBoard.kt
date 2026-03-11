package com.example.cityfix.pages.adminPage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cityfix.ui.theme.MainBG
import com.example.cityfix.uiComponents.AdminBottomBar
import com.example.cityfix.uiComponents.AdminHeader
import com.example.cityfix.uiComponents.DashboardChart

data class Dashboard(
    val title: String,
    val number: String,
)

@Composable
fun DashBoard(navController: NavController?) {
    val isPreview = LocalInspectionMode.current
    val currentRoute = if (isPreview) {
        "admin" // Default for preview
    } else {
        navController?.currentBackStackEntryAsState()?.value?.destination?.route
    }

    val dashboardItems = remember {
        listOf(
            Dashboard("Total Issue", "125"),
            Dashboard("New Issue", "10"),
            Dashboard("Issue Fixed", "20"),
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

                ElevatedCard(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Total Issues: 124", modifier = Modifier.padding(16.dp))
                }

                ElevatedCard(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("New Issues: 110", modifier = Modifier.padding(16.dp))
                }

                ElevatedCard(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Issues Fixed: 20", modifier = Modifier.padding(16.dp))
                }

                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Issues Overview",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        // Call the chart function we made above
                        DashboardChart()
                    }
                }

            }



        }
    }
}

@Preview(showBackground = true, name = "DashBoard Page")
@Composable
fun PreviewAdmin(){
            DashBoard(navController = null)
        }