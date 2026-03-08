package com.example.cityfix.pages.AdminPage.IssueTabs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cityfix.ui.theme.MainBG
import com.example.cityfix.uiComponents.AdminHeader
import com.example.cityfix.uiComponents.IssueItem
import com.example.cityfix.uiComponents.IssueSorterBar
import com.example.cityfix.uiComponents.StatusBadge
import kotlin.collections.filter

// --- THE RECYCLABLE TEMPLATE ---
@Composable
fun IssueTabTemplate(
    pageTitle: String,
    totalCount: String,
    newCount: String,
    issueList: List<IssueItem>,
    navController: NavController?
) {
    var selectedFilter by remember { mutableStateOf("All") }

    Scaffold(
        topBar = { AdminHeader(title = pageTitle, navController = navController) }
    ) { paddingValues ->
        // APPLY paddingValues HERE to fix the red error
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // This uses the Scaffold's padding
                .MainBG()
        ) {
            // Stats Section
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard("Total: $totalCount", Modifier.weight(1f))
                StatCard("New: $newCount", Modifier.weight(1f))
            }

            // Sorter
            IssueSorterBar(
                selectedCategory = selectedFilter,
                onCategorySelected = { selectedFilter = it }
            )

            // The Filtered List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val filteredList = if (selectedFilter == "All") issueList
                else issueList.filter { it.status == selectedFilter }

                items(filteredList) { item ->
                    IssueCard(item)
                }
            }
        }
    }
}

// --- THE SPECIFIC HAZARDS SCREEN ---
@Composable
fun Hazards(navController: NavController?) {
    // This is where you put your specific data for this page
    val hazardData = remember {
        listOf(
            IssueItem("Broken Power Line", "123 Main St", "Pending", "10 minutes ago", "Urgent"),
            IssueItem("Exposed Wires", "456 Oak Ave", "Ongoing", "10 minutes ago", "High"),
            IssueItem("Leaning Pole", "789 Pine Rd", "Resolved", "10 minutes ago", "Medium"),
            IssueItem("Transformer Sparking", "Downtown Alley", "Pending", "10 minutes ago", "Normal"),
            IssueItem("Downed Cable", "Westside Park", "Ongoing", "10 minutes ago", "Urgent")
        )
    }

    IssueTabTemplate(
        pageTitle = "Hazard Reports",
        totalCount = "124",
        newCount = "110",
        issueList = hazardData,
        navController = navController
    )
}

// --- UI COMPONENTS (REUSABLE) ---

@Composable
fun StatCard(text: String, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun IssueCard(item: IssueItem) {
    val urgencyColor = when (item.urgency) {
        "Urgent" -> Color(0xFFD32F2F)   // Strong Red
        "High" -> Color(0xFFFF9800)     // Orange
        "Medium" -> Color(0xFFFBC02D)   // Yellow/Amber
        else -> Color.Gray              // Low or Normal
    }

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                StatusBadge(item.status)
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Show the Urgency and Time
            Row {
                Text(
                    text = item.urgency,
                    style = MaterialTheme.typography.bodySmall,
                    color = urgencyColor,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = " • ${item.time}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Location: ${item.location}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HazardsPreview() {
    Hazards(navController = null)
}