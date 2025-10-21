package com.example.loginpage_three

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.loginpage_three.databinding.ActivityRegisterPageBinding

class RegisterPage : AppCompatActivity() {
    private val PREFS_NAME = "my_prefs"
    private val KEY_USERNAME = "key_username"
    private val KEY_EMAIL = "key_email"

    private lateinit var binding: ActivityRegisterPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get SharedPreferences to store and retrieve data
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Load saved data when the activity starts
        loadSavedData(prefs)

        // Automatically save data when user types
        binding.usernameInputTXT.addTextChangedListener {
            saveData(prefs)
        }
        binding.emailregTXT.addTextChangedListener {
            saveData(prefs)
        }

        // Set up button click
        binding.loginBTN.setOnClickListener {
            val username = binding.usernameInputTXT.text.toString().trim()
            val email = binding.emailregTXT.text.toString().trim()
            val password = binding.PasswordregTXT.text.toString().trim()
            val repassword = binding.rePasswordregTXT.text.toString().trim()

            when {
                username.isEmpty() -> {
                    Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show()
                }
                username.length < 4 -> {
                    Toast.makeText(this, "Username must be at least 4 characters", Toast.LENGTH_SHORT).show()
                }
                username.length > 15 -> {
                    Toast.makeText(this, "Username cannot be longer than 15 characters", Toast.LENGTH_SHORT).show()
                }

                username.matches(Regex("^[A-Za-z0-9._]+$")) -> {
                    Toast.makeText(this, "Only letters, numbers, underscores, and dots are allowed", Toast.LENGTH_SHORT).show()
                }
                username.contains("..") || username.contains("__") -> {
                    Toast.makeText(this, "No consecutive dots or underscores allowed", Toast.LENGTH_SHORT).show()
                }

                email.isEmpty() -> {
                    Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                }
                password.isEmpty() -> {
                    Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
                }
                password.length < 8 -> {
                    Toast.makeText(this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
                }
                !password.matches(Regex(".*[A-Z].*")) -> {
                    Toast.makeText(this, "Password must contain at least one uppercase letter", Toast.LENGTH_SHORT).show()
                }
                !password.matches(Regex(".*[a-z].*")) -> {
                    Toast.makeText(this, "Password must contain at least one lowercase letter", Toast.LENGTH_SHORT).show()
                }
                !password.matches(Regex(".*\\d.*")) -> {
                    Toast.makeText(this, "Password must contain at least one number", Toast.LENGTH_SHORT).show()
                }
                !password.matches(Regex(".*[!@#\$%^&*(),.?\":{}|<>].*")) -> {
                    Toast.makeText(this, "Password must contain at least one special character", Toast.LENGTH_SHORT).show()
                }


                repassword.isEmpty() -> {
                    Toast.makeText(this, "Please confirm your password", Toast.LENGTH_SHORT).show()
                }
                password != repassword -> {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // ✅ All checks passed
                    Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        // Handle sign-in text click
        binding.signinTV.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    // ✅ Function to save data
    private fun saveData(prefs: SharedPreferences) {
        val username = binding.usernameInputTXT.text.toString().trim()
        val email = binding.emailregTXT.text.toString().trim()

        val editor = prefs.edit()
        editor.putString(KEY_USERNAME, username)
        editor.putString(KEY_EMAIL, email)
        editor.apply() // apply() saves asynchronously
    }

    // ✅ Function to load saved data
    private fun loadSavedData(prefs: SharedPreferences) {
        val savedName = prefs.getString(KEY_USERNAME, "")
        val savedEmail = prefs.getString(KEY_EMAIL, "")

        binding.usernameInputTXT.setText(savedName)
        binding.emailregTXT.setText(savedEmail)
    }
}
