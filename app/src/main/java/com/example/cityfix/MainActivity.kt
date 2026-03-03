package com.example.cityfix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cityfix.pages.AdminPage
import com.example.cityfix.pages.LoginScreen
import com.example.cityfix.ui.theme.AppStrings
import com.example.cityfix.ui.theme.ButtonText
import com.example.cityfix.ui.theme.CITYFIXTheme
import com.example.cityfix.ui.theme.Logo
import com.example.cityfix.ui.theme.MainBG
import com.example.cityfix.ui.theme.appName
import com.example.cityfix.ui.theme.contentBox
import com.example.cityfix.ui.theme.contentText
import com.example.cityfix.ui.theme.conttext
import com.example.cityfix.ui.theme.headerContainer
import com.example.cityfix.ui.theme.headertext
import com.example.cityfix.ui.theme.tutorialBox

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        enableEdgeToEdge()
        setContent {
            CITYFIXTheme(darkTheme = false) {
                val navController = rememberNavController()
                // 2. The NavHost manages which screen to show
                NavHost(navController = navController, startDestination = "home") {
                    composable("login") {
                        LoginScreen(navController)
                    }
                    composable("home") {
                        Greeting(navController)
                    }
                    composable("adminpage"){
                        AdminPage(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(navController: androidx.navigation.NavController) {
    Column(Modifier.MainBG()) {

        // Header Container
        Box(modifier = Modifier.headerContainer()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(5.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pic_user),
                    contentDescription = "User Image",
                    modifier = Modifier.Logo()
                )
                Text(text = AppStrings.APP_NAME, style = appName)

                Spacer(modifier = Modifier.weight(1f))

                // Login Link
                Text(
                    text = AppStrings.LOGIN_BUTTON,
                    style = ButtonText,
                    modifier = Modifier
                        .headertext()
                        .clickable {
                            navController.navigate("login")
                        }
                )

                // Sign Up Link
                Text(
                    text = AppStrings.SIGN_UP_BUTTON,
                    style = ButtonText,
                    modifier = Modifier.headertext()
                )
            }
        }

        // Welcome' Box
        Box(modifier = Modifier.contentBox()) {
            Column {
                Text(text = AppStrings.WELCOME_MESSAGE, style = conttext, modifier = Modifier.contentText())
                Text(text = AppStrings.APP_DESCRIPTION, style = conttext, modifier = Modifier.contentText())
            }
        }

        // The Scrollable List
        LazyColumn(
            modifier = Modifier.padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(6) {
                Column(
                    modifier = Modifier.tutorialBox(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.pic_logo),
                        contentDescription = "Logo",
                        modifier = Modifier.Logo()
                    )
                    Text(text = "Tutorial", style = conttext, modifier = Modifier.contentText())
                }
            }
        }
    }
}

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        CITYFIXTheme {
            // We use a "dummy" controller for the preview
            val navController = rememberNavController()
            Greeting(navController = navController)
        }
    }