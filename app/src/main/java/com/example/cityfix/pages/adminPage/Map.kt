package com.example.cityfix.pages.adminPage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
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
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.cityfix.R

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
    val category: String
)

// Sample Data
val allMapIssues = listOf(
    MapIssue("Kulang sa IT", GeoPoint(6.742454, 125.358471), "Water"),
//    MapIssue("Downed Pole", GeoPoint(-1.288, 36.820), "Power"),
//    MapIssue("Pothole", GeoPoint(-1.290, 36.815), "Roads"),
//    MapIssue("Pothole", GeoPoint(-1.299, 36.815), "Waste"),
//    MapIssue("Pothole", GeoPoint(-1.305, 36.815), "Trees"),
//    MapIssue("Pothole", GeoPoint(-1.203, 36.815), "Lights")

)

@Composable
fun MapScreen(navController: NavController?) {
    val isPreview = LocalInspectionMode.current

    // 1. Track which category is selected
    var selectedCategory by remember { mutableStateOf("All") }

    Scaffold(
        topBar = { AdminHeader(title = "City Issue Map", navController = navController) },
        bottomBar = { AdminBottomBar(navController = navController, currentRoute = "map") }
    ) { innerPadding ->
        // Use a Box to overlay buttons ON TOP of the map
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {

            // --- THE MAP LAYER ---
            if (isPreview) {
                Box(Modifier.fillMaxSize().background(Color.LightGray), contentAlignment = Alignment.Center) {
                    Text("Map View")
                }
            } else {
                AndroidView(
                    factory = { ctx ->
                        MapView(ctx).apply {
                            setTileSource(TileSourceFactory.MAPNIK)

                            // --- THIS ENABLES PINCH ZOOM ---
                            setMultiTouchControls(true)

                            // Set initial view
                            controller.setZoom(15.0)
                            controller.setCenter(GeoPoint(6.742454, 125.358471))
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                    update = { mapView ->
                        mapView.overlays.clear()

                        val composebg = Color(0xA9FFFFFF)

                        val filteredMarkers = allMapIssues.filter {
                            selectedCategory == "All" || it.category == selectedCategory
                        }

                        filteredMarkers.forEach { issue ->
                            val marker = Marker(mapView)
                            marker.position = issue.point
                            marker.title = issue.title

                            val (iconRes, bgColor) = when (issue.category) {
                                "Water" -> Pair(R.drawable.pic_water, composebg.toArgb())
                                "Power" -> Pair(R.drawable.pic_bolt, composebg.toArgb())
                                "Roads" -> Pair(R.drawable.pic_road, composebg.toArgb())
                                "Lights" -> Pair(R.drawable.pic_light, composebg.toArgb())
                                "Hazards" -> Pair(R.drawable.pic_hazard, composebg.toArgb())
                                "Trees" -> Pair(R.drawable.pic_trees, composebg.toArgb())
                                "Waste" -> Pair(R.drawable.pic_trash, composebg.toArgb())
                                else -> Pair(R.drawable.pic_trash, composebg.toArgb())
                            }

                            marker.icon = createCustomMarker(
                                context = mapView.context,
                                iconResId = iconRes,
                                backgroundColor = bgColor,
                                sizePx = 70 // I bumped this to 100 so it's easier to see!
                            )

                            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            mapView.overlays.add(marker)
                        }
                        mapView.invalidate()
                    }
                )
            }

            // --- THE SORTER LAYER (Floating on top) ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .horizontalScroll(rememberScrollState()), // Allows many categories
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val categories = listOf("All", "Water", "Power", "Roads", "Hazards", "Lights", "Trees", "Waste")
                categories.forEach { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        label = { Text(category) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MapPreview() {
    MapScreen(navController = null)
}