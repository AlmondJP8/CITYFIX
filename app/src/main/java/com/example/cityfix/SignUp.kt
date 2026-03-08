package com.example.cityfix

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.example.cityfix.ui.theme.MainBG

@Composable
fun SignUpScreen(navController: NavController? = null) { // 1. Pass the NavController here
    Column (modifier = Modifier
        .MainBG(),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Spacer(modifier = Modifier.weight(0.1f))

        Image(
            painter = painterResource(id = R.drawable.pic_logo),
            contentDescription = "Logo",
            modifier = Modifier .size(100.dp) .clip(CircleShape)
        )

        Spacer(modifier = Modifier.weight(0.01f))

        ElevatedCard(
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier.fillMaxWidth(0.85f),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "CityFix",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF1976D2)
                )

                Text(text = "Sign in to continue", color = Color.Gray)

                Spacer(modifier = Modifier.height(12.dp))

                // Input Fields (You'll want to add 'remember' states here later)
                OutlinedTextField(
                    value = "", onValueChange = {},
                    label = { Text("First Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = "", onValueChange = {},
                    label = { Text("Last Name") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = "", onValueChange = {},
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = "", onValueChange = {},
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 2. The Main Button: Now connected to Navigation
                Button(onClick = {
                    // Navigate only if the controller isn't null
                    navController?.navigate("adminpage")
                },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1976D2),
                        contentColor = Color.White
                    )
                ) {
                    Text("Sign Up", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text("Already have an Account? Log In")
            }
        }
        Spacer(modifier = Modifier.weight(0.1f))
    }
}

// PREVIEW MUST BE OUTSIDE THE MAIN FUNCTION
@Preview(showBackground = true, name = "SignUp View")
@Composable
fun PreviewSignUp() {
    SignUpScreen()
}