package com.example.post_covid_attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    private lateinit var nameField: EditText
    private lateinit var emailField: EditText
    private lateinit var signupButton: Button
    private lateinit var signInButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        nameField = findViewById(R.id.edit_signup_name)
        emailField = findViewById(R.id.edit_signup_email)
        signupButton = findViewById(R.id.button_signup)

        signupButton.setOnClickListener {
            val name = nameField.text.toString()
            val email = emailField.text.toString()

            if (isValidEmail(email)) {
                val passwordSetupIntent = Intent(this, PasswordSetupActivity::class.java)
                startActivity(passwordSetupIntent)

            } else {
                emailField.error = "Please enter a valid email address"
            }
        }

        signInButton.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}