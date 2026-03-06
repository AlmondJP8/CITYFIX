package com.example.cityfix.pages.AdminPage

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.example.cityfix.ui.theme.Logo
import com.example.cityfix.ui.theme.MainBG
import com.example.cityfix.ui.theme.appName
import com.example.cityfix.ui.theme.gridText
import com.example.cityfix.ui.theme.headerContainer
import com.example.cityfix.ui.theme.headertext
import com.example.cityfix.uiComponents.AdminBottomBar

data class DashboardItem(
    val title: String,
    val iconRes: Int,
    val route: String,
    val color: Color
)

@Composable
fun AdminPage(navController: NavController?) {
    // 1. Check if we are in the Preview window
    val isPreview = LocalInspectionMode.current
    // 2. Only "listen" to the backstack if we are NOT in the preview
    val currentRoute = if (isPreview) {
        "admin" // Default for preview
    } else {
        navController?.currentBackStackEntryAsState()?.value?.destination?.route
    }

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
        Column(
            modifier = Modifier
                .MainBG()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 2. The Header - No horizontal padding on the Box so it spans the full width
            Box(modifier = Modifier.headerContainer().fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 10.dp, start = 16.dp, end = 16.dp) // Manual padding for status bar area
                ) {
                    Text(text = "Admin Control Panel", style = appName)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = AppStrings.LOGIN_BUTTON, style = ButtonText, modifier = Modifier.headertext())
                }
            }

            // 3. Apply the innerPadding and horizontal padding HERE (to the content only)
            Column(
                modifier = Modifier
                    .padding(bottom = innerPadding.calculateBottomPadding()) // Respect the bottom bar
                    .padding(horizontal = 16.dp), // Keep sides clean
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                // Stats Cards
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

                // The Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(GridConfig.COLUMNS),
                    contentPadding = PaddingValues(top = 10.dp, bottom = 20.dp),
                    verticalArrangement = GridConfig.VERTICAL_SPACING,
                    horizontalArrangement = GridConfig.HORIZONTAL_SPACING,
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(dashboardItems) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(110.dp)
                                .clickable { navController?.navigate(item.route) },
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
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

@Preview(showBackground = true, name = "Admin Page")
@Composable
fun PreviewAdmin(){
            AdminPage(navController = null)
        }