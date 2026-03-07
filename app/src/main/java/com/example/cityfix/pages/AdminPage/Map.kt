package com.example.cityfix.pages.AdminPage

import android.content.Context
import org.osmdroid.config.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cityfix.uiComponents.AdminBottomBar
import com.example.cityfix.uiComponents.AdminHeader
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun MapScreen(navController: NavController) {

    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current

    // 1. Listen to the backstack safely
    val currentRoute = if (isPreview) {
        "map"
    } else {
        navController.currentBackStackEntryAsState().value?.destination?.route
    }

    // Initialize OSM configuration (Required for tile caching)
    Configuration.getInstance().userAgentValue = context.packageName
    Configuration.getInstance().load(context, context.getSharedPreferences("osm_pref", Context.MODE_PRIVATE))

    Scaffold(
        topBar = {
            // Use your reusable AdminHeader file here
            AdminHeader(title = "City Issue Map")
        },
        bottomBar = {
            // Keep your existing BottomBar here
            AdminBottomBar(navController = navController, currentRoute = currentRoute)
        }
    ) { innerPadding ->
        // The Map goes in the "Content" area
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // This prevents the map from hiding behind bars
        ) {
            AndroidView(
                factory = { ctx ->
                    MapView(ctx).apply {
                        setTileSource(TileSourceFactory.MAPNIK)
                        setMultiTouchControls(true)
                        controller.setZoom(15.0)
                        controller.setCenter(GeoPoint(-1.286, 36.817))

                        val marker = Marker(this)
                        marker.position = GeoPoint(-1.286, 36.817)
                        marker.title = "Water Pipe Leak"
                        this.overlays.add(marker)
                    }
                },
                modifier = Modifier.fillMaxSize()
            )

            //more content
        }
    }


}