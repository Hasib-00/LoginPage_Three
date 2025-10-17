package com.example.loginpage_three

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginpage_three.databinding.ActivityRegisterPageBinding

class RegisterPage : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up button click
        binding.loginBTN.setOnClickListener {
            val username = binding.usernameInputTXT.text.toString().trim()
            val email = binding.emailregTXT.text.toString().trim()
            val password = binding.PasswordregTXT.text.toString().trim()
            val repassword = binding.rePasswordregTXT.text.toString().trim()

            if (username.isEmpty()) {
                Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show()
            } else {
                if (email.isEmpty()) {
                    Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                } else {
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT)
                            .show()
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
                                if (repassword.length < 8) {
                                    Toast.makeText(
                                        this,
                                        "Password must be at least 8 characters",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    if (repassword.isEmpty()) {
                                        Toast.makeText(
                                            this,
                                            "Please confirm your password",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        if (password != repassword) {
                                            Toast.makeText(
                                                this,
                                                "Passwords do not match",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            // ✅ All checks passed
                                            Toast.makeText(
                                                this,
                                                "Signup Successful",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            val intent = Intent(this, MainActivity::class.java)
                                            startActivity(intent)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        binding.signinTV.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}