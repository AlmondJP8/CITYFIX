package com.example.cityfix.uiComponents

data class IssueItem(
    val title: String,
    val location: String,
    val status: String,
    val time: String, // e.g., "2 hours ago"
    val urgency: String       // e.g., "High", "Medium", "Low"
)