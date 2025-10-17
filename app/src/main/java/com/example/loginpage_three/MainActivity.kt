package com.example.loginpage_three

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginpage_three.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBTN.setOnClickListener{
            val email = binding.emailinputTXT.text.toString()
            val password = binding.inputPasswordTXT.text.toString()


            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            } else {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                } else {
                    if (password.isEmpty()) {
                        Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
                    } else {
                        if (password.length < 8) {
                            Toast.makeText(this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
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
}