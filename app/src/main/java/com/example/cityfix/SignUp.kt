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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

import com.example.cityfix.ui.theme.MainBG
import com.google.firebase.firestore.firestore

@Composable
fun SignUpScreen(navController: NavController? = null) {
    // 1. Firebase and Context setup
    val auth = remember { Firebase.auth }
    val context = LocalContext.current

    // 2. States for Input Fields
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    // In your SignUpScreen code:
    val userProfile = hashMapOf(
        "firstName" to firstName,
        "lastName" to lastName,
        "email" to email,
        "role" to "user" // Default role
    )

    // 3. Success Dialog logic
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Account Created!") },
            text = { Text("Welcome to CityFix! You can now log in to report urban issues.") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    navController?.navigate("login") {
                        popUpTo("signup") { inclusive = true }
                    }
                }) {
                    // Shortened this for better UI fit in the button
                    Text("OK", color = Color(0xFF1976D2), fontWeight = FontWeight.Bold)
                }
            }
        )
    }

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

                Text(text = "Create an account to continue", color = Color.Gray)

                Spacer(modifier = Modifier.height(12.dp))

                // Input Fields linked to state
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Last Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // The functional Sign Up Button
                Button(
                    onClick = {
                        if (email.isNotEmpty() && password.isNotEmpty()) {
                            if (password.length < 6) {
                                Toast.makeText(context, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                            } else {
                            auth.createUserWithEmailAndPassword(email.trim(), password.trim())
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val userId = auth.currentUser?.uid
                                        val db = Firebase.firestore

                                        // UPDATED: Added the role here so it actually saves to the database
                                        val userProfile = hashMapOf(
                                            "firstName" to firstName,
                                            "lastName" to lastName,
                                            "email" to email,
                                            "role" to "user"
                                        )

                                        if (userId != null) {
                                            db.collection("users").document(userId)
                                                .set(userProfile)
                                                .addOnSuccessListener {
                                                    showDialog = true
                                                }
                                                .addOnFailureListener { e ->
                                                    android.widget.Toast.makeText(context, "Database Error: ${e.message}", android.widget.Toast.LENGTH_SHORT).show()
                                                }
                                        }
                                    } else {
                                        android.widget.Toast.makeText(context, "Auth Failed: ${task.exception?.message}", android.widget.Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        } else {
                            android.widget.Toast.makeText(context, "Fields cannot be empty", android.widget.Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1976D2),
                        contentColor = Color.White
                    )
                ) {
                    Text("Sign Up", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row {
                    Text("Already have an Account? ")
                    Text(
                        text = "Log In",
                        color = Color(0xFF1976D2),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            navController?.navigate("login")
                        }
                    )
                }
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