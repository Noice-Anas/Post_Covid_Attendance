package com.example.post_covid_attendance

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class PasswordSetupActivity : AppCompatActivity() {

    private lateinit var passwordField: EditText
    private lateinit var confirmPasswordField: EditText
    private lateinit var finishButton: Button
    private lateinit var databaseManager: DatabaseManager
    private lateinit var name: String
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_setup)

        passwordField = findViewById(R.id.edit_password_setup_new)
        confirmPasswordField = findViewById(R.id.edit_password_setup_confirm)
        finishButton = findViewById(R.id.button_password_setup)
        databaseManager = DatabaseManager(this)
        name = intent.getStringExtra("name")!!
        email = intent.getStringExtra("email")!!

        finishButton.setOnClickListener {
            val password = passwordField.text.toString()
            val confirmPassword = confirmPasswordField.text.toString()

            if (isValidPassword(password) && password == confirmPassword) {
                databaseManager.insertUser(name, email, password)
                // User was successfully added to the database
                // TODO: Add code to handle successful signup
            } else {
                passwordField.error = "Please enter a valid password"
                confirmPasswordField.error = "Passwords do not match"
            }
        }
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }
}
