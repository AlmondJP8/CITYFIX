package com.example.cityfix.pages.adminPage.IssueTabs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.cityfix.ui.theme.MainBG
import com.example.cityfix.uiComponents.AdminHeader
import com.example.cityfix.uiComponents.IssueItem
import com.example.cityfix.uiComponents.IssueTabTemplate
import com.google.firebase.firestore.firestore

// --- THE RECYCLABLE TEMPLATE ---
@Composable
fun Hazards(navController: NavController?) {
    val db = com.google.firebase.Firebase.firestore

    // 1. State to hold the live data from Firebase
    var hazardData by remember { mutableStateOf(listOf<IssueItem>()) }
    var isLoading by remember { mutableStateOf(true) }

    // 2. Connect to the "issues" collection
    LaunchedEffect(Unit) {
        db.collection("Issues")
            .whereEqualTo("category", "Hazards") // Only get hazards!
            .addSnapshotListener { value, error ->
                if (error == null && value != null) {
                    hazardData = value.documents.map { doc ->
                        // Map the Firebase fields to your IssueItem model
                        IssueItem(
                            description = doc.getString("title") ?: "Untitled",
                            location = doc.getString("location") ?: "Unknown Location",
                            status = doc.getString("status") ?: "Pending",
                            time = "Just now", // You can format doc.getTimestamp("created_at") here later
                            urgency = doc.getString("priority") ?: "Normal"
                        )
                    }
                }
                isLoading = false
            }
    }

    Scaffold(
        topBar = { AdminHeader(title = "Hazard Report", navController = navController) }
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
                    totalCount = hazardData.size,
                    newCount = hazardData.count { it.status == "Pending" },
                    issues = hazardData,
                    navController = navController
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HazardsPreview() {
    Hazards(navController = null)
}