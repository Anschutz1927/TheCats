package com.example.thecatapi.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.thecatapi.databinding.ActivitySplashBinding
import com.example.thecatapi.utils.OnTransitionCompleted

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        binding.motion.setTransitionListener(object : OnTransitionCompleted {
            override fun onTransitionCompleted() = onAnimationFinished()
        })
    }

    private fun onAnimationFinished() {
        finish()
        startMainActivity()
    }

    private fun startMainActivity() = startActivity(Intent(this, MainActivity::class.java))
}
