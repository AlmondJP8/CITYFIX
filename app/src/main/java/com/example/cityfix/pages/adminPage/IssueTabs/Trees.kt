package com.example.cityfix.pages.adminPage.IssueTabs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.cityfix.ui.theme.MainBG
import com.example.cityfix.uiComponents.AdminHeader
import com.example.cityfix.uiComponents.IssueItem
import com.example.cityfix.uiComponents.IssueTabTemplate
import com.google.firebase.firestore.firestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Trees(navController: NavController?) { // <-- Change this name for Water, Lights, etc.
    val db = com.google.firebase.Firebase.firestore

    // 1. State to hold the live data from Firebase
    var treesData by remember { mutableStateOf(listOf<IssueItem>()) }
    var isLoading by remember { mutableStateOf(true) }

    // 2. Connect to the "issues" collection
    LaunchedEffect(Unit) {
        db.collection("Issues") // Ensure this matches your capital "I" collection
            .whereEqualTo("category", "Trees") // Filters for the "Toxic Waste" entry
            .addSnapshotListener { value, error ->
                if (error == null && value != null) {
                    treesData = value.documents.map { doc ->
                        IssueItem(
                            description = doc.getString("description") ?: "No Description",
                            location = doc.getString("location") ?: "Unknown Location",
                            status = doc.getString("status") ?: "Pending",
                            urgency = doc.getString("urgency") ?: "Normal",
                            time = doc.getTimestamp("time")?.toDate()?.time ?: 0L

                        )
                    }
                }
                isLoading = false
            }
    }

    Scaffold(
        topBar = { AdminHeader(title = "Trees Report Issue") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .MainBG()
        ) {
            if (isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                // 3. Pass the live firebase data to your template
                IssueTabTemplate(
                    totalCount = treesData.size,
                    newCount = treesData.count { it.status == "Pending" },
                    issues = treesData,
                    navController = navController
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Trees Screen")
@Composable
fun TreesPreview(){
    Trees(navController = null)
}