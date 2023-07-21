package com.groupthree.blocklink

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Handle the login button click
        findViewById<Button>(R.id.button_login).setOnClickListener {
            val email = findViewById<EditText>(R.id.edittext_email).text.toString()
            val password = findViewById<EditText>(R.id.edittext_password).text.toString()

            // Log in the user
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // The user is logged in
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // The login failed
                        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}