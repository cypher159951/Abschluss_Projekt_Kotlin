package com.example.paymessage


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.paymessage.databinding.ActivityMainBinding
import com.example.paymessage.ui.ArtikelFragmentDirections
import com.example.paymessage.ui.FavoriteFragment
import com.example.paymessage.ui.FavoriteFragmentDirections
import com.example.paymessage.ui.HomeFragment
import com.example.paymessage.ui.HomeFragmentDirections
import com.example.paymessage.ui.SettingsFragment
import com.example.paymessage.ui.SettingsFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private val fragmentManager: FragmentManager = supportFragmentManager
    private lateinit var currentFragment: Fragment

    private fun switchFragment(fragment: Fragment) {
        fragmentManager.commit {
            replace(R.id.fragmentContainerView, fragment)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    private val onItemSelectedListener =
        NavigationBarView.OnItemSelectedListener  { item ->
            when (item.itemId) {
                R.id.favorite -> {
                    switchFragment(FavoriteFragment())
                    return@OnItemSelectedListener true
                }
                R.id.settings -> {
                    switchFragment(SettingsFragment())
                    return@OnItemSelectedListener true
                }
                R.id.home -> {
                    switchFragment(HomeFragment())
                    return@OnItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        try {
            binding.bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.favorite -> {

                        /*val currentDestination = navController.currentDestination
                        if (currentDestination?.id != R.id.favoriteFragment) {
                            //Navigate to Favorite
                            navController.navigate(
                                R.id.favoriteFragment
                            )
                        }*/
                        switchFragment(FavoriteFragment())
                        true
                    }


                    R.id.settings -> {

                        /*val currentDestination = navController.currentDestination
                        if (currentDestination?.id != R.id.settingsFragment) {
                            //Navigate to Favorite
                            navController.navigate(
                                R.id.settingsFragment
                            )
                        }*/
                        switchFragment(SettingsFragment())
                        true
                    }


                    R.id.home -> {
                        /*val currentDestination = navController.currentDestination
                        if (currentDestination?.id != R.id.homeFragment) {
                            //Navigate to Favorite
                            navController.navigate(
                                R.id.homeFragment
                            )
                        }*/
                        switchFragment(HomeFragment())
                        true
                    }


                    else -> {// Standard-Navigation zu anderen Fragmenten
                        NavigationUI.onNavDestinationSelected(item, navController!!)
                        navController?.popBackStack(item.itemId, false)
                        true
                    }
                }

            }
        } catch (
            e: Exception
        ) {
            Log.e("Settings", "${e}")
        }
    }
}