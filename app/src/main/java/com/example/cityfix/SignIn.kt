package com.example.cityfix

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore // Need this for the DB
import com.example.cityfix.ui.theme.MainBG

@Composable
fun LoginScreen(navController: NavController? = null) {
    val auth = remember { Firebase.auth }
    val db = remember { Firebase.firestore } // Initialize Firestore here
    val context = LocalContext.current

    // MUST add these states or the text fields won't work
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.MainBG(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.1f))

        Image(
            painter = painterResource(id = R.drawable.pic_logo),
            contentDescription = "Logo",
            modifier = Modifier.size(100.dp).clip(CircleShape)
        )

        Spacer(modifier = Modifier.weight(0.01f))

        ElevatedCard(
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier.fillMaxWidth(0.85f).padding(bottom = 20.dp),
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

                Text(text = "Log in to continue", color = Color.Gray)

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (email.isNotEmpty() && password.isNotEmpty()) {
                            // STEP 1: Sign In First
                            auth.signInWithEmailAndPassword(email.trim(), password.trim())
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        // STEP 2: Check Role in Firestore
                                        val userId = auth.currentUser?.uid
                                        if (userId != null) {
                                            db.collection("users").document(userId).get()
                                                .addOnSuccessListener { document ->
                                                    if (document.exists()) {
                                                        val role = document.getString("role")
                                                        if (role == "admin") {
                                                            navController?.navigate("dashboard") {
                                                                popUpTo("login") { inclusive = true }
                                                            }
                                                        } else {
                                                            navController?.navigate("submission") {
                                                                popUpTo("login") { inclusive = true }
                                                            }
                                                        }
                                                    }
                                                }
                                        }
                                    } else {
                                        Toast.makeText(context, "User profile not found in database.", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1976D2),
                        contentColor = Color.White
                    )
                ) {
                    Text("LOG IN", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    Text(text = "Don't have an account? ", color = Color.Gray)
                    Text(
                        text = "Sign Up",
                        color = Color(0xFF1976D2),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            navController?.navigate("signup")
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(0.1f))
    }
}