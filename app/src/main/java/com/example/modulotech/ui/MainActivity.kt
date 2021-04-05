package com.example.modulotech.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.modulotech.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var mNavHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mNavHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment

        findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
            .setupWithNavController(navController = mNavHostFragment.navController)

        mNavHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.title = destination.label
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                mNavHostFragment.navController.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
