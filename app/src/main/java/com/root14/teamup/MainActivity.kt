package com.root14.teamup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.root14.teamup.databinding.ActivityLoginBinding
import com.root14.teamup.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}