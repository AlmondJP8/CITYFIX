package com.example.cityfix.uiComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlin.collections.filter

data class IssueItem(
    val description: String,
    val location: String,
    val status: String,
    val time: String,
    val urgency: String
)
@Composable
fun IssueTabTemplate(
    issues: List<IssueItem>, // We'll define this data class below
    navController: NavController?,
    totalCount: Int, newCount: Int
) {
    var selectedFilter by remember { mutableStateOf("All") }

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
        val filteredList = if (selectedFilter == "All") issues
        else issues.filter { it.status == selectedFilter }

        items(filteredList) { item ->
            IssueCard(item)
        }
    }
}

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
                    text = item.description,
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