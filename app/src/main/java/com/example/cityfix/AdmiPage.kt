package com.example.cityfix

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.navigation.currentBackStackEntryAsState
import com.example.cityfix.ui.theme.Grid
import com.example.cityfix.ui.theme.GridConfig
import com.example.cityfix.ui.theme.MainBG
import com.example.cityfix.ui.theme.appName
import com.example.cityfix.ui.theme.gridText

data class DashboardItem(
    val title: String,
    val iconRes: Int,
    val route: String,
    val color: Color
)

@Composable
fun AdminPage(navController: NavController) {
    // This line "listens" to where the user is in the app
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

//    val context = LocalContext.current
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.TakePicturePreview()
//    ) { bitmap ->
//        // 'bitmap' is the photo the user just took!
//        // You can save this or display it in an Image()
//    }
//
//    Button(onClick = { launcher.launch(null) }) {
//        Text("Take Photo of Issue")
//    }

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
        // 3. Your Main Admin Content
        Column(
            modifier = Modifier
                .MainBG()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Admin Control Panel", style = appName)

            Spacer(modifier = Modifier.height(20.dp))

            // Your Admin stats or cards go here
            ElevatedCard(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Total Issues: 124", modifier = Modifier.padding(16.dp))
            }

            ElevatedCard(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("New Issues: 10", modifier = Modifier.padding(16.dp))
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(GridConfig.COLUMNS),
                contentPadding = GridConfig.CONTENT_PADDING,
                verticalArrangement = GridConfig.VERTICAL_SPACING,
                horizontalArrangement = GridConfig.HORIZONTAL_SPACING,
                modifier = Modifier.fillMaxSize().padding(top = 10.dp)
            ) {
                items(dashboardItems) { item -> // 'item' is now a DashboardItem object
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp), // Increased height slightly to prevent text cutoff
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White // Keeps the card clean
                        ),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        // Use fillMaxSize so the Box centers content inside the Card
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    painter = painterResource(id = item.iconRes), // Dynamic Icon from list
                                    contentDescription = item.title,
                                    modifier = Modifier.size(35.dp),
                                    tint = Color.Unspecified
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = item.title,
                                    style = gridText,
                                    fontSize = 14.sp,
                                    color = item.color
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}


@Preview(showBackground = true, name = "Admin Page")
@Composable
fun PreviewAdmin(){
    val navController = androidx.navigation.compose.rememberNavController()
    AdminPage(navController = navController)
}