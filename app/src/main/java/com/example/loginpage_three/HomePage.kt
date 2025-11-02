package com.example.loginpage_three

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.loginpage_three.databinding.ActivityHomePageBinding
import com.example.loginpage_three.databinding.FragmentHomeBinding

class HomePage : AppCompatActivity() {
    private lateinit var binding: ActivityHomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Navbar backend Start

        replaceFragment(HomeFragment())

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homeicone -> replaceFragment(HomeFragment())
                R.id.square -> replaceFragment(GgFragment())
                R.id.profileicone -> replaceFragment(ProfileFragment())
            }
            true
        }

    }

    private fun replaceFragment (fragment: Fragment){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main,fragment)
            .commit()
    }

    //Navbar backend end
}

