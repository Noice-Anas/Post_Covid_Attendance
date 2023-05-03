package com.example.post_covid_attendance

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignInActivity : AppCompatActivity() {

    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var signInButton: Button
    private lateinit var databaseManager: DatabaseManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        emailField = findViewById(R.id.edit_email)
        passwordField = findViewById(R.id.edit_password)
        signInButton = findViewById(R.id.button_sign_in)

        signInButton.setOnClickListener {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            val dbManager = DatabaseManager(this)
            val user = dbManager.getUserByEmail(email)

            if (user != null && user.password == password) {
                // Credentials match, navigate to the main screen
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Credentials don't match, show error message
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}