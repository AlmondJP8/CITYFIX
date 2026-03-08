package com.example.cityfix.uiComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cityfix.pages.AdminPage.IssueTabs.IssueCard
import com.example.cityfix.pages.AdminPage.IssueTabs.StatCard
import com.example.cityfix.ui.theme.MainBG
import kotlin.collections.filter

@Composable
fun IssueTabTemplate(
    title: String,
    totalCount: Int,
    newCount: Int,
    issues: List<IssueItem>, // We'll define this data class below
    navController: NavController?
) {
    var selectedFilter by remember { mutableStateOf("All") }

    Scaffold(
        topBar = { AdminHeader(title = title) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                val filteredList = if (selectedFilter == "All") issues
                else issues.filter { it.status == selectedFilter }

                items(filteredList) { item ->
                    IssueCard(item)
                }
            }
        }
    }
}