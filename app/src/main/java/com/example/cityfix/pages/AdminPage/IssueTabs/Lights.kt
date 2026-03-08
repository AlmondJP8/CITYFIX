package com.example.cityfix.pages.AdminPage.IssueTabs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cityfix.ui.theme.MainBG
import com.example.cityfix.uiComponents.AdminHeader
import com.example.cityfix.uiComponents.IssueSorterBar
import com.example.cityfix.uiComponents.StatusBadge
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Lights(navController: NavController?) {
    // 1. State for the filter
    var selectedFilter by remember { mutableStateOf("All") }

    Scaffold(
        topBar = {
            // Use your custom header instead of the default TopAppBar
            // This ensures all your pages look the same!
            AdminHeader(title = "Light Reports")
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .MainBG() // Apply your background theme here
        ) {

            // --- SECTION 1: STATS (Horizontal for better space) ---
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard1("Total: 124", Modifier.weight(1f))
                StatCard1("New: 110", Modifier.weight(1f))
            }

            // --- SECTION 2: THE SORTER ---
            IssueSorterBar(
                selectedCategory = selectedFilter,
                onCategorySelected = { selectedFilter = it }
            )

            // --- SECTION 3: THE LIST ---
            // Note: If 'allIssues' isn't defined yet, you can create a dummy list
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Temporary dummy data logic
                val dummyIssues = listOf("Pending", "Ongoing", "Resolved", "Pending")

                val filteredList = if (selectedFilter == "All") dummyIssues
                else dummyIssues.filter { it == selectedFilter }

                items(filteredList.size) { index ->
                    IssueCard1(status = filteredList[index])
                }
            }
        }
    }
}

// Helper component for those stats cards to keep code clean
@Composable
fun StatCard1(text: String, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

// A better Issue Card design
@Composable
fun IssueCard1(status: String) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Broken Power Line", style = MaterialTheme.typography.titleMedium)
                StatusBadge(status) // Uses your custom Badge
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("Location: 123 Main St", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview(showBackground = true, name = "Light Screen")
@Composable
fun LightsPreview(){
    Lights(navController = null)
}