package com.example.cityfix.pages.adminPage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.cityfix.uiComponents.AdminBottomBar
import com.example.cityfix.uiComponents.AdminHeader
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import com.example.cityfix.R
import com.example.cityfix.uiComponents.MapSorter
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

// Helper to get a Drawable and convert it for the Map
fun createCustomMarker(
    context: Context,
    iconResId: Int,
    backgroundColor: Int, // The background color (e.g., Color.BLUE)
    sizePx: Int // The target size (e.g., 60)
): Drawable {
    // 1. Create the blank canvas
    val bitmap = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    // 2. Draw the background shape (e.g., a circle)
    paint.color = backgroundColor
    val rect = RectF(0f, 0f, sizePx.toFloat(), sizePx.toFloat())
    canvas.drawOval(rect, paint)

    // 3. Draw the PNG icon on top
    val icon = ContextCompat.getDrawable(context, iconResId)
    if (icon != null) {
        // Calculate padding (e.g., 20% from the edge)
        val padding = sizePx / 5
        icon.setBounds(padding, padding, sizePx - padding, sizePx - padding)
        icon.draw(canvas)
    }
    return BitmapDrawable(context.resources, bitmap)
}

data class MapIssue(
    val title: String,
    val point: GeoPoint,
    val category: String,
    val description: String,
    val urgency: String,
    val locationName: String // Added this to match your screenshot
)

//// Sample Data
//val allMapIssues = listOf(
//(6.742454, 125.358471), "Water"),
//(6.749764, 125.350934), "Power"),
//(6.750700, 125.356373), "Waste"),
//(6.749545, 125.355485), "Roads"),
//(6.747414, 125.355485), "Lights"),
//(6.748926, 125.355485), "Trees"),
//(6.745311, 125.355485), "Hazards"),
//)

@Composable
fun MapScreen(navController: NavController?) {
    val isPreview = LocalInspectionMode.current
    val db = Firebase.firestore

    var firebaseIssues by remember { mutableStateOf(listOf<MapIssue>()) }
    var selectedCategory by remember { mutableStateOf("All") }

    LaunchedEffect(Unit) {
        db.collection("Issues").addSnapshotListener { value, error ->
            if (error != null) {
                android.util.Log.e("MAP_DEBUG", "Firebase Error: ${error.message}")
                return@addSnapshotListener
            }

            if (value != null) {
                android.util.Log.d("MAP_DEBUG", "Found ${value.documents.size} total docs in 'issues'")

                firebaseIssues = value.documents.mapNotNull { doc ->
                    val title = doc.getString("description")
                    val latRaw = doc.get("latitude")
                    val lonRaw = doc.get("longitude")

                    // This will print to your Logcat exactly what is coming from the cloud
                    android.util.Log.d("MAP_DEBUG", "Doc: ${doc.id} | Lat: $latRaw (${latRaw?.javaClass?.simpleName})")

                    // Safely convert whatever type comes in (Long or Double) to Double
                    val lat = (latRaw as? Number)?.toDouble() ?: 0.0
                    val lon = (lonRaw as? Number)?.toDouble() ?: 0.0
                    val cat = doc.getString("category") ?: ""
                    val urg = doc.getString("urgency") ?: "Normal"
                    val loc = doc.getString("location") ?: "Unknown"

                    if (lat != 0.0 && lon != 0.0) {
                        MapIssue(title ?: "No Desc", GeoPoint(lat, lon), cat, title ?: "", urg, loc)
                    } else {
                        android.util.Log.w("MAP_DEBUG", "Skipping ${doc.id} - Lat/Lon is 0.0 or wrong type")
                        null
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = { AdminHeader(title = "City Issue Map", navController = navController) },
        bottomBar = { AdminBottomBar(navController = navController, currentRoute = "map") }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            if (isPreview) {
                Box(Modifier.fillMaxSize().background(Color.LightGray), contentAlignment = Alignment.Center) {
                    Text("Map View")
                }
            } else {
                AndroidView(
                    factory = { ctx ->
                        MapView(ctx).apply {
                            org.osmdroid.config.Configuration.getInstance().userAgentValue = ctx.packageName
                            setTileSource(TileSourceFactory.MAPNIK)
                            setMultiTouchControls(true)

                            controller.setZoom(16.0)
                            controller.setCenter(GeoPoint(6.742454, 125.358471))

                            this.onResume()
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                    update = { mapView ->
                        mapView.overlays.clear()
                        val composebg = Color(0xC1FFFFFF).toArgb()

                        firebaseIssues.filter {
                            selectedCategory == "All" || it.category == selectedCategory
                        }.forEach { issue ->
                            val iconRes = when (issue.category) {
                                "Water" -> R.drawable.pic_water
                                "Power" -> R.drawable.pic_bolt
                                "Roads" -> R.drawable.pic_road
                                "Lights" -> R.drawable.pic_light
                                "Hazards" -> R.drawable.pic_hazard
                                "Trees" -> R.drawable.pic_trees
                                "Waste" -> R.drawable.pic_trash
                                else -> null
                            }

                            if (iconRes != null) {
                                val marker = Marker(mapView)
                                marker.position = issue.point

                                // Enhanced Info Window
                                marker.title = issue.description
                                marker.snippet = "Location: ${issue.locationName}"
                                marker.subDescription = "Urgency: ${issue.urgency}"

                                marker.icon = createCustomMarker(
                                    context = mapView.context,
                                    iconResId = iconRes,
                                    backgroundColor = composebg,
                                    sizePx = 70
                                )

                                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
                                mapView.overlays.add(marker)
                            }
                        }
                        mapView.invalidate()
                    }
                )
            }
            MapSorter(
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MapPreview() {
    MapScreen(navController = null)
}