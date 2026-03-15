package com.example.cityfix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cityfix.pages.UserPage.SubmissionPage

import com.example.cityfix.pages.adminPage.DashBoard
import com.example.cityfix.pages.adminPage.MapScreen
import com.example.cityfix.pages.adminPage.ReportsPage
import com.example.cityfix.pages.adminPage.IssueTabs.Hazards
import com.example.cityfix.pages.adminPage.IssueTabs.Lights
import com.example.cityfix.pages.adminPage.IssueTabs.Power
import com.example.cityfix.pages.adminPage.IssueTabs.Streets
import com.example.cityfix.pages.adminPage.IssueTabs.Trees
import com.example.cityfix.pages.adminPage.IssueTabs.Waste
import com.example.cityfix.pages.adminPage.IssueTabs.Water

import com.example.cityfix.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        enableEdgeToEdge()
        org.osmdroid.config.Configuration.getInstance().userAgentValue = "CityFixApp"

        // This ensures the system splash disappears immediately
        splashScreen.setOnExitAnimationListener { viewProvider ->
            viewProvider.remove()
        }

        setContent {
            CITYFIXTheme(darkTheme = false) {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "splash_screen") {

                    //animated slpashscreen
                    composable(route = "splash_screen",
                        exitTransition = {
                            fadeOut(animationSpec = tween(500)) +
                                    scaleOut(targetScale = 0.9f, animationSpec = tween(500))
                        }
                    ) {
                        SplashScreen(navController)
                    }

                    // Main App Routes
                    composable("start", enterTransition = {
                            // How the dashboard arrives: it fades in and "pops" up
                            fadeIn(animationSpec = tween(800)) +
                                    scaleIn(initialScale = 0.8f, animationSpec = tween(600, easing = EaseOutBack))
                        }) {Greeting(navController)}
                    composable("login", enterTransition = {
                        fadeIn(animationSpec = tween(800)) +
                                scaleIn(initialScale = 0.8f, animationSpec = tween(600, easing = EaseOutBack))
                    }) {LoginScreen(navController)}
                    composable("signup", enterTransition = {
                        fadeIn(animationSpec = tween(800)) +
                                scaleIn(initialScale = 0.8f, animationSpec = tween(600, easing = EaseOutBack))
                    }){SignUpScreen(navController)}

                    //For User
                    composable("submission") {
                        SubmissionPage(navController, onSuccess = { navController.popBackStack() })
                    }

                    //For admin page
                    composable("dashboard") { DashBoard(navController) }
                    composable("reports"){ReportsPage(navController)}
                    composable ("map"){ MapScreen(navController) }

                    // Category Routes for admin
                    composable("power") {Power(navController)}
                    composable("water") {Water(navController)}
                    composable("lights") {Lights(navController)}
                    composable("road") {Streets(navController)}
                    composable("hazards") {Hazards(navController)}
                    composable("trees") {Trees(navController)}
                    composable("waste") {Waste(navController)}


                }
            }
        }
    }
}

@Composable
fun Greeting(navController: androidx.navigation.NavController) {
    Column(Modifier.MainBG()) {
        Box(modifier = Modifier.headerContainer()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(5.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pic_logo),
                    contentDescription = "User",
                    modifier = Modifier.Logo()
                )
                Text(text = AppStrings.APP_NAME,
                    style = appName,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = AppStrings.LOGIN_BUTTON,
                    style = ButtonText,
                    modifier = Modifier.headertext().clickable { navController.navigate("login") }
                )

                Text(text = AppStrings.SIGN_UP_BUTTON,
                    style = ButtonText,
                    modifier = Modifier.headertext().clickable{ navController.navigate("signup")})
            }
        }

        Box(modifier = Modifier.contentBox()) {
            Column {
                Text(text = AppStrings.WELCOME_MESSAGE, style = conttext, modifier = Modifier.contentText())
                Text(text = AppStrings.APP_DESCRIPTION, style = conttext, modifier = Modifier.contentText())
            }
        }

        LazyColumn(modifier = Modifier.padding(5.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(6) {
                Column(modifier = Modifier.tutorialBox(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painter = painterResource(id = R.drawable.pic_logo), contentDescription = "Logo", modifier = Modifier.Logo())
                    Text(text = "Tutorial", style = conttext, modifier = Modifier.contentText())
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Greeting Screen")
@Composable
fun GreetingPreview() {
    CITYFIXTheme(darkTheme = false) {
        // Use a "fake" controller just for the preview window
        val navController = rememberNavController()
        Surface(color = MaterialTheme.colorScheme.background) {
            Greeting(navController = navController)
        }
    }
}