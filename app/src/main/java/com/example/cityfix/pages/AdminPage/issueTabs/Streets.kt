package com.example.cityfix.pages.AdminPage.issueTabs

import androidx.compose.foundation.layout.*
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
fun Streets(navController: NavController?) { // <-- Change this name for Water, Lights, etc.
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
            Text("Report a new power issue or view existing status here.")
            // Your form or list goes here
        }
    }
}

@Preview(showBackground = true, name = "Streets Screen")
@Composable
fun StreetsPreview(){
    Streets(navController = null)
}