package com.example.thecatapi.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.thecatapi.R
import com.example.thecatapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val navController = (fragment as NavHostFragment).navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_all_cats, R.id.navigation_favorite_cats)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
    }
}