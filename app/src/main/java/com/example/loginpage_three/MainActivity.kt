package com.example.loginpage_three

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.loginpage_three.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private lateinit var binding: ActivityMainBinding

    private val PREFS_NAME = "my_prefs"
    private val KEY_EMAIL = "key_email"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // ✅ Initialize view binding correctly
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // ✅ Load saved email data
        loadSavedData(prefs)

        // ✅ Save email automatically when typed
        binding.emailinputTXT.addTextChangedListener {
            saveData(prefs)
        }

        // ✅ Clear email when end icon clicked
        binding.emailInput.setEndIconOnClickListener {
            binding.emailinputTXT.setText("")
            val editor = prefs.edit()
            editor.remove(KEY_EMAIL)
            editor.apply()
        }

        // ✅ Handle login button click
        binding.loginBTN.setOnClickListener {
            val email = binding.emailinputTXT.text.toString().trim()
            val password = binding.inputPasswordTXT.text.toString().trim()

            when {
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
                else -> {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                showToast("Login Successful")
                                val intent = Intent(this, HomePage::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                showToast("Login Failed: ${task.exception?.message}")
                            }
                        }
                }
            }
        }

        // ✅ Go to Register Page
        binding.signupTV.setOnClickListener {
            startActivity(Intent(this, RegisterPage::class.java))
        }

        // ✅ Go to Forgot Password Page
        binding.forgotpassTV.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordPageOne::class.java))
        }
    }

    // ✅ Save email to SharedPreferences
    private fun saveData(prefs: SharedPreferences) {
        val email = binding.emailinputTXT.text.toString().trim()
        if (email.isNotEmpty()) {
            val editor = prefs.edit()
            editor.putString(KEY_EMAIL, email)
            editor.apply()
        }
    }

    // ✅ Load saved email
    private fun loadSavedData(prefs: SharedPreferences) {
        val savedEmail = prefs.getString(KEY_EMAIL, "")
        binding.emailinputTXT.setText(savedEmail)
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

    // ✅ Helper: reusable toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}