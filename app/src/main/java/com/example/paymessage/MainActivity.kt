package com.example.paymessage


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.paymessage.databinding.ActivityMainBinding
import com.example.paymessage.ui.ArtikelFragmentDirections
import com.example.paymessage.ui.HomeFragmentDirections
import com.example.paymessage.ui.SettingsFragmentDirections
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

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.settings -> {
                    //  val navController = findNavController(R.id.fragmentContainerView)

                    val currentDestination = navController.currentDestination
                    if (currentDestination?.id == R.id.artikelFragment) {
                        val action = HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
                        navController.navigate(action)
                    } else if (currentDestination?.id == R.id.homeFragment) {
                        val action = HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
                        navController.navigate(action)
                    }
                    true
                }

                R.id.settings -> {
                    val currentDestination = navController.currentDestination
                    if (currentDestination?.id == R.id.artikelFragment) {
                        // Wenn das aktuelle Ziel das Detailfragment ist, navigiere zu den Einstellungen
                        val action = ArtikelFragmentDirections.actionArtikelFragmentToSettingsFragment()
                        navController.navigate(action)
                    } else {
                        // Wenn das aktuelle Ziel nicht das Detailfragment ist, navigiere zum Homefragment
                        val action = ArtikelFragmentDirections.actionArtikelFragmentToSettingsFragment()
                        navController.navigate(action)
                    }
                    true
                }

                R.id.home -> {
                    val navController = findNavController(R.id.fragmentContainerView)
                    val currentDestination = navController.currentDestination

                    if (currentDestination?.id == R.id.artikelFragment) {
                        val action = ArtikelFragmentDirections.actionArtikelFragmentToHomeFragment()
                    } else if (currentDestination?.id == R.id.settingsFragment) {
                        val action =
                            SettingsFragmentDirections.actionSettingsFragmentToHomeFragment()
                        navController.navigate(action)
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
    }
}