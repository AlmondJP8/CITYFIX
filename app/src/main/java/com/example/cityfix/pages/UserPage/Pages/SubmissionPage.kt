package com.example.cityfix.pages.UserPage

import android.Manifest
import android.app.Activity
import android.content.IntentSender
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cityfix.ui.theme.MainBG
import com.example.cityfix.uiComponents.AdminHeader
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

val report = hashMapOf(
    "category" to "",
    "status" to "",
    "timestamp" to FieldValue.serverTimestamp() // ADD THIS
)

@Composable
fun SubmissionPage(navController: NavController, onSuccess: () -> Unit) {
    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current
    val db = FirebaseFirestore.getInstance()
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = if (isPreview) "submission" else navBackStackEntry?.destination?.route

    // Form states
    var description by remember { mutableStateOf("") }
    var locationName by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    var selectedUrgency by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) }

    val categories = listOf("Hazards", "Water", "Power", "Roads", "Waste", "Lights", "Trees")
    val urgencies = listOf("Low", "Normal", "Urgent")

    // --- LOGIC FUNCTIONS ---

    fun uploadIssue(lat: Double, lng: Double) {
        val newIssue = hashMapOf(
            "description" to description,
            "location" to locationName,
            "category" to selectedCategory,
            "urgency" to selectedUrgency,
            "status" to "Pending",
            "timestamp" to FieldValue.serverTimestamp(),
            "latitude" to lat,
            "longitude" to lng
        )

        db.collection("Issues").add(newIssue)
            .addOnSuccessListener {
                isSubmitting = false
                onSuccess()
            }
            .addOnFailureListener { isSubmitting = false }
    }

    fun getLocationAndUpload() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener { location ->
                    if (location != null) {
                        uploadIssue(location.latitude, location.longitude)
                    } else {
                        fusedLocationClient.lastLocation.addOnSuccessListener { lastLoc ->
                            uploadIssue(lastLoc?.latitude ?: 0.0, lastLoc?.longitude ?: 0.0)
                        }
                    }
                }
        }
    }

    // --- LAUNCHERS (Must be inside the Composable) ---

    val settingResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            getLocationAndUpload()
        } else {
            isSubmitting = false
        }
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
            isSubmitting = true
            getLocationAndUpload()
        }
    }

    // Function to trigger the "Turn on GPS" popup
    fun checkSettingsAndSubmit() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(context)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener { getLocationAndUpload() }
        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                try {
                    val intentSenderRequest = IntentSenderRequest.Builder(exception.resolution.intentSender).build()
                    settingResultLauncher.launch(intentSenderRequest)
                } catch (e: IntentSender.SendIntentException) { }
            }
        }
    }

    // --- UI ---
    Scaffold(
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .MainBG()
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            AdminHeader(title = "Submit Issue", navController = navController)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Report an Urban Issue", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("What is the issue?") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = locationName,
                onValueChange = { locationName = it },
                label = { Text("Location Description") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text("Category", style = MaterialTheme.typography.titleMedium)
            ScrollableRow(categories, selectedCategory) { selectedCategory = it }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Urgency Level", style = MaterialTheme.typography.titleMedium)
            ScrollableRow(urgencies, selectedUrgency) { selectedUrgency = it }

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val hasPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    if (hasPermission) {
                        isSubmitting = true
                        checkSettingsAndSubmit()
                    } else {
                        locationPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = description.isNotEmpty() && locationName.isNotEmpty() && !isSubmitting
            ) {
                if (isSubmitting) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                } else {
                    Text("Submit Report")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScrollableRow(items: List<String>, selected: String, onSelect: (String) -> Unit) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { item ->
            FilterChip(
                selected = (item == selected),
                onClick = { onSelect(item) },
                label = { Text(item) }
            )
        }
    }
}