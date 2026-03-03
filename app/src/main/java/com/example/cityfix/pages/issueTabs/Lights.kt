package com.example.cityfix.pages.issueTabs

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Lights(navController: NavController) { // <-- Change this name for Water, Lights, etc.
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Power Issues") }, // <-- Change title per screen
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            Text("Report a new power issue or view existing status here.")
            // Your form or list goes here
        }
    }
}