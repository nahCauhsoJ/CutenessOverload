package com.example.cutenessoverload

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cutenessoverload.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val navController by lazy { findNavController(R.id.nav_host_fragment_activity_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_pic, R.id.navigation_saved_pic)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_pic -> {
                    if (navController.currentDestination ==
                        navController.graph.findNode(R.id.picFragment)) {
                        false
                    } else {
                        navController
                            .navigate(R.id.action_savedPicFragment_to_picFragment)
                        true
                    }
                }
                R.id.navigation_saved_pic -> {
                    if (navController.currentDestination ==
                        navController.graph.findNode(R.id.savedPicFragment)) {
                        false
                    } else {
                        navController
                            .navigate(R.id.action_picFragment_to_savedPicFragment)
                        true
                    }
                }
                else -> false
            }
        }
    }
}