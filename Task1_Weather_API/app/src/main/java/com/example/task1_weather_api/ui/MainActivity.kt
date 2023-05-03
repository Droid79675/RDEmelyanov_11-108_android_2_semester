package com.example.task1_weather_api.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.task1_weather_api.R

class MainActivity : AppCompatActivity() {

    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        controller = (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
    }
}
