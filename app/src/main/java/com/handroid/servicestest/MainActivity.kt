package com.handroid.servicestest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.handroid.servicestest.databinding.ActivityMainBinding
import com.handroid.servicestest.services.MyService

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.tvSimpleService.setOnClickListener {
           startService(MyService.newIntent(this))
        }
    }
}