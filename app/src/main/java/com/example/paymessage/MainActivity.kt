package com.example.paymessage


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.paymessage.databinding.ActivityMainBinding
import com.example.paymessage.ui.ArtikelFragmentDirections
import com.example.paymessage.ui.FavoriteFragmentDirections
import com.example.paymessage.ui.HomeFragmentDirections
import com.example.paymessage.ui.SettingsFragmentDirections
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        try {
            binding.bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.favorite -> {
//                        val action =
//                            HomeFragmentDirections.actionHomeFragmentToFavoriteFragment()
//                        navController.navigate(action)
                        val currentDestination = navController.currentDestination
                        if (currentDestination?.id != R.id.favoriteFragment) {
                            //Navigate to Favorite
                            navController.navigate(
                                R.id.favoriteFragment
                            )
                        }
                        true
                    }


                    R.id.settings -> {

                        val currentDestination = navController.currentDestination
                        if (currentDestination?.id != R.id.settingsFragment) {
                            //Navigate to Favorite
                            navController.navigate(
                                R.id.settingsFragment
                            )
                        }
                        true
                    }


                    R.id.home -> {
                        val currentDestination = navController.currentDestination
                        if (currentDestination?.id != R.id.homeFragment) {
                            //Navigate to Favorite
                            navController.navigate(
                                R.id.homeFragment
                            )
                        }
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