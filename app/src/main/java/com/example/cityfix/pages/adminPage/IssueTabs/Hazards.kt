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
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore

// --- THE RECYCLABLE TEMPLATE ---
@Composable
fun Hazards(navController: NavController?) {
    val db = com.google.firebase.Firebase.firestore
    var hazardData by remember { mutableStateOf(listOf<IssueItem>()) }
    var lastCheckTime by remember { mutableLongStateOf(0L) }
    var isLoading by remember { mutableStateOf(true) }

    // 2. Connect to the "issues" collection
    LaunchedEffect(Unit) {
        db.collection("AdminView").document("usage_stats")
            .get().addOnSuccessListener { doc ->
                lastCheckTime = doc.getTimestamp("viewstats")?.toDate()?.time ?: 0L
            }

        db.collection("Issues")
            .whereEqualTo("category", "Hazards")
            .addSnapshotListener { value, error ->
                if (error == null && value != null) {
                    hazardData = value.documents.map { doc ->
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

    // 2. Update "Last Checked" when the Admin leaves the page
    DisposableEffect(Unit) {
        onDispose {
            val now = com.google.firebase.Timestamp.now()
            db.collection("AdminView").document("usage_stats")
                .set(mapOf("viewstats" to now), SetOptions.merge())
        }
    }

    // 3. Logic: An issue is "New" if it was created AFTER the last time Admin checked the page
    val newIssuesCount = hazardData.count { it.time > lastCheckTime }

    Scaffold(
        topBar = { AdminHeader(title = "Hazard Report Issue", navController = navController) }
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
                    newCount =newIssuesCount,
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