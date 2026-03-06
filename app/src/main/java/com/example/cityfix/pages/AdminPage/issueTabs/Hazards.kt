package com.example.cityfix.pages.AdminPage.issueTabs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Hazards(navController: NavController?) { // <-- Change this name for Water, Lights, etc.
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Power Issues") }, // <-- Change title per screen
                navigationIcon = {
                    IconButton(onClick = { navController?.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
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

            Text("Report a new power issue or view existing status here.")

        }
    }
}

@Preview(showBackground = true, name = "Hazards Screen")
@Composable
fun HazardsPreview(){
    Hazards(navController = null)
}