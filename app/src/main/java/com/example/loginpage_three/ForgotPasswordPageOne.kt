package com.example.loginpage_three

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.loginpage_three.databinding.ActivityForgotPasswordPageOneBinding

class ForgotPasswordPageOne : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordPageOneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password_page_one)

        binding = ActivityForgotPasswordPageOneBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.forgotoneBTN.setOnClickListener {
            val email = binding.forgotoneinputPasswordTXT.text.toString()

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this, Verifypage::class.java)
                startActivity(intent)
            }






        }









    }
}