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

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val PREFS_NAME = "my_prefs"
    private val KEY_EMAIL = "key_email"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get SharedPreferences to store and retrieve data
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)


        // Load saved data into the EditText fields when the app starts
        loadSavedData(prefs)


        // Set up the end icon click listener for email input field
        binding.emailInput.setEndIconOnClickListener {
            // Clear the email EditText
            binding.emailinputTXT.setText("")
            // Clear the email from SharedPreferences
            val editor = prefs.edit()
            editor.remove(KEY_EMAIL)
            editor.apply()  // Apply changes asynchronously
        }


        // Save the data when the user types in the EditText fields
        binding.emailinputTXT.addTextChangedListener {
            saveData(prefs)
        }



        binding.loginBTN.setOnClickListener {
            val email = binding.emailinputTXT.text.toString()
            val password = binding.inputPasswordTXT.text.toString()


            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            } else {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                } else {
                    if (password.isEmpty()) {
                        Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        if (password.length < 8) {
                            Toast.makeText(
                                this,
                                "Password must be at least 8 characters",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, HomePage::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
        }



        binding.signupTV.setOnClickListener {
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)

        }



        binding.forgotpassTV.setOnClickListener {
            val intent = Intent(this, ForgotPasswordPageOne::class.java)
            startActivity(intent)
        }

    }


    // Function to save data into SharedPreferences
    private fun saveData(prefs: SharedPreferences) {
        val email = binding.emailinputTXT.text.toString().trim()

        // Save the data only if the name is not empty
        if (email.isNotEmpty()) {
            val editor = prefs.edit()
            editor.putString(KEY_EMAIL, email)
            editor.apply()

        }

    }

    private fun loadSavedData(prefs: SharedPreferences) {
        val savedName = prefs.getString(KEY_EMAIL, "")

        // Set the saved data in the EditText fields
        binding.emailinputTXT.setText(savedName)

    }
    // This function runs every time you touch anywhere on the screen
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (currentFocus != null) {  // Check if something (like an EditText) is currently focused
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            // Hide the keyboard
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            // Remove focus from the EditText so the cursor disappears
            currentFocus!!.clearFocus()
        }
        // Pass the touch event to the rest of the system
        return super.dispatchTouchEvent(ev)
    }
}