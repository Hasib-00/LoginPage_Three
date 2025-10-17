package com.example.loginpage_three

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginpage_three.databinding.ActivityVerifypageBinding

class Verifypage : AppCompatActivity() {

    private lateinit var binding: ActivityVerifypageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_verifypage)
        binding = ActivityVerifypageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.verifyBTN.setOnClickListener {
            val digitone = binding.otp1.text.toString()
            val digittwo = binding.otp2.text.toString()
            val digitthree = binding.otp3.text.toString()
            val digitfour = binding.otp4.text.toString()


            if (digitone.isEmpty()) {
                Toast.makeText(this, "Enter your First OTP", Toast.LENGTH_SHORT).show()
            } else {
                if (digittwo.isEmpty()) {
                    Toast.makeText(this, "Enter your Second OTP", Toast.LENGTH_SHORT).show()
                } else {
                    if (digitthree.isEmpty()) {
                        Toast.makeText(this, "Enter your Third OTP", Toast.LENGTH_SHORT).show()

                    } else {
                        if (digitfour.isEmpty()) {
                            Toast.makeText(this, "Enter your Fourth OTP", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "OTP Verified", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)

                        }
                    }
                }

            }
        }


    }


}

