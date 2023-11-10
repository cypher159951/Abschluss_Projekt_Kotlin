package com.example.paymessage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.paymessage.data.SharedPreferences.AppPreferences
import com.example.paymessage.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Um im Nachmodus zu starten wenn es zuletzt gespeichert / eingestellt war
        val nightModeEnabled = AppPreferences.getNightMode(this)
        setNightMode(nightModeEnabled)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        try {
            binding.bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.favorite -> {
                        val currentDestination = navController.currentDestination
                        if (currentDestination?.id != R.id.favoriteFragment) {
                            navController.navigate(
                                R.id.favoriteFragment
                            )
                        }
                        true
                    }

                    R.id.settings -> {
                        val currentDestination = navController.currentDestination
                        if (currentDestination?.id != R.id.settingsFragment) {
                            navController.navigate(
                                R.id.settingsFragment
                            )
                        }
                        true
                    }

                    R.id.home -> {
                        val currentDestination = navController.currentDestination
                        if (currentDestination?.id != R.id.homeFragment) {
                            navController.navigate(
                                R.id.homeFragment
                            )
                        }
                        true
                    }

                    else -> {   // Standard-Navigation zu anderen Fragmenten
                        NavigationUI.onNavDestinationSelected(item, navController!!)
                        navController?.popBackStack(item.itemId, false)
                        true
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("Settings", "${e}")
        }
    }


    //Funktion für nachtmodus, checken ob nachmodus an oder aus war
    private fun setNightMode(isNightModeEnabled: Boolean) {
        if (isNightModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}


