package com.example.loginpage_three  // 👈 make sure this matches your actual app package name

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.loginpage_three.databinding.ActivityVerifypageBinding

class Verifypage : AppCompatActivity() {

    private lateinit var binding: ActivityVerifypageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // ✅ Use only binding to load the layout
        binding = ActivityVerifypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Now your EditTexts exist through binding
        val otp1 = binding.otp1
        val otp2 = binding.otp2
        val otp3 = binding.otp3
        val otp4 = binding.otp4

        // ✅ Setup automatic focus movement
        otp1.addTextChangedListener(nextTextWatcher(otp2))
        otp2.addTextChangedListener(nextTextWatcher(otp3, otp1))
        otp3.addTextChangedListener(nextTextWatcher(otp4, otp2))
        otp4.addTextChangedListener(nextTextWatcher(null, otp3))

        // ✅ Verify button click
        binding.verifyBTN.setOnClickListener {
            val digitone = otp1.text.toString()
            val digittwo = otp2.text.toString()
            val digitthree = otp3.text.toString()
            val digitfour = otp4.text.toString()

            when {
                digitone.isEmpty() -> Toast.makeText(this, "Enter your First OTP", Toast.LENGTH_SHORT).show()
                digittwo.isEmpty() -> Toast.makeText(this, "Enter your Second OTP", Toast.LENGTH_SHORT).show()
                digitthree.isEmpty() -> Toast.makeText(this, "Enter your Third OTP", Toast.LENGTH_SHORT).show()
                digitfour.isEmpty() -> Toast.makeText(this, "Enter your Fourth OTP", Toast.LENGTH_SHORT).show()
                else -> {
                    Toast.makeText(this, "OTP Verified", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    // ✅ TextWatcher to move cursor automatically
    private fun nextTextWatcher(next: EditText?, prev: EditText? = null): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                when {
                    s?.length == 1 -> next?.requestFocus()
                    s?.isEmpty() == true -> prev?.requestFocus()
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        }
    }
}
