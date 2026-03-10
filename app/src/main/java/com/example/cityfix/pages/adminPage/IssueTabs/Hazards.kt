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

// --- THE RECYCLABLE TEMPLATE ---
@Composable
fun Hazards(navController: NavController?) {

    // Only keep the data here
    val hazardData = remember {
        listOf(
            IssueItem("Broken Power Line", "123 Main St", "Pending", "10 mins ago", "Urgent"),
            IssueItem("Exposed Wires", "456 Oak Ave", "Ongoing", "20 mins ago", "High"),
            IssueItem("Leaning Pole", "246 jake St", "Pending", "1hr ago", "Normal"),
            IssueItem("Transformer Sparking", "Downtown Alley", "Ongoing", "35 mins ago", "Medium"),
            IssueItem("Downed Cable", "Westside Park", "Resolved", "40 mins ago", "Urgent")

        )
    }

    Scaffold(
        topBar = { AdminHeader(title = "Hazard Report") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .MainBG()
        ) {

            // Just call the template!
            IssueTabTemplate(
                totalCount = 124,
                newCount = 110,
                issues = hazardData,
                navController = navController
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HazardsPreview() {
    Hazards(navController = null)
}