package com.example.loginpage_three

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.loginpage_three.databinding.ActivityRegisterPageBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterPage : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
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

        // Handle registration
        binding.loginBTN.setOnClickListener {
            val username = binding.usernameInputTXT.text.toString().trim()
            val email = binding.emailregTXT.text.toString().trim()
            val password = binding.PasswordregTXT.text.toString().trim()
            val repassword = binding.rePasswordregTXT.text.toString().trim()

            when {
                username.isEmpty() -> {
                    showToast("Please enter your username")
                }

                username.length < 4 -> {
                    showToast("Username must be at least 4 characters")
                }

                username.length > 15 -> {
                    showToast("Username cannot be longer than 15 characters")
                }

                !username.matches(Regex("^[A-Za-z0-9]+$")) -> {
                    showToast("Username Only letters, numbers,are allowed")
                }

                username.contains("..") || username.contains("__") -> {
                    showToast("No consecutive dots or underscores allowed")
                }

                email.isEmpty() -> {
                    showToast("Please enter your email")
                }

                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    showToast("Please enter a valid email")
                }

                password.isEmpty() -> {
                    showToast("Please enter your password")
                }

                password.length < 8 -> {
                    showToast("Password must be at least 8 characters")
                }

                !password.matches(Regex(".*[A-Z].*")) -> {
                    showToast("Password must contain at least one uppercase letter")
                }

                !password.matches(Regex(".*[a-z].*")) -> {
                    showToast("Password must contain at least one lowercase letter")
                }

                !password.matches(Regex(".*\\d.*")) -> {
                    showToast("Password must contain at least one number")
                }

                !password.matches(Regex(".*[!@#\$%^&*(),.?\":{}|<>].*")) -> {
                    showToast("Password must contain at least one special character")
                }

                repassword.isEmpty() -> {
                    showToast("Please confirm your password")
                }

                password != repassword -> {
                    showToast("Passwords do not match")
                }

                else -> {
                    // ✅ Create user in Firebase
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                showToast("Signup Successful")
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                showToast("Signup Failed: ${task.exception?.message}")
                            }
                        }
                }
            }
        }

        // Handle sign-in text click
        binding.signinTV.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    // ✅ Helper: Save username & email
    private fun saveData(prefs: SharedPreferences) {
        val username = binding.usernameInputTXT.text.toString().trim()
        val email = binding.emailregTXT.text.toString().trim()

        val editor = prefs.edit()
        editor.putString(KEY_USERNAME, username)
        editor.putString(KEY_EMAIL, email)
        editor.apply() // apply() saves asynchronously
    }

    // ✅ Helper: Load username & email
    private fun loadSavedData(prefs: SharedPreferences) {
        val savedName = prefs.getString(KEY_USERNAME, "")
        val savedEmail = prefs.getString(KEY_EMAIL, "")

        binding.usernameInputTXT.setText(savedName)
        binding.emailregTXT.setText(savedEmail)
    }

    // ✅ Helper: Reusable Toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    // ✅ Hide keyboard when touching outside EditText
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        currentFocus?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            it.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }

}