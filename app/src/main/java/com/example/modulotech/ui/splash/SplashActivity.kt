package com.example.modulotech.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.modulotech.R
import com.example.modulotech.di.Injection
import com.example.modulotech.ui.MainActivity

class SplashActivity : AppCompatActivity() {

    private val mSplashViewModel: SplashViewModel by viewModels {
        Injection.provideSplashViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mSplashViewModel.loadResources().observe(this, {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        })
    }
}
