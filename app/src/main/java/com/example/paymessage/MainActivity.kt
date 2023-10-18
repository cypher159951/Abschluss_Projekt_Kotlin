package com.example.paymessage


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.paymessage.databinding.ActivityMainBinding
import com.example.paymessage.ui.NewsViewModel
import java.lang.Exception




// Die Hauptaktivität, die die verschiedenen Fragmente der Anwendung verwaltet.
class MainActivity : AppCompatActivity() {



    // Eine Instanz des NavController, der für die Navigation zwischen den Fragmenten verantwortlich ist.
    private lateinit var navController: NavController

    // Eine Instanz der View-Bindungsklasse für die Aktivität.
    lateinit var binding: ActivityMainBinding

    // Die Methode, die aufgerufen wird, wenn die Aktivität erstellt wird.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Einrichten der View-Bindung für die Aktivität.
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Finden des NavHostFragment und Initialisierung des NavController.
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        // Die Sichtbarkeit der Bottom Navigation Bar auf der Hauptaktivität ausblenden.
        //binding.bottomNavigationView.visibility = View.GONE

        try {
            // Setzen des ItemSelectedListeners für die Bottom Navigation Bar.
            binding.bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.favorite -> {
                        // Navigiere zum Favoriten-Fragment, wenn ausgewählt.
                        val currentDestination = navController.currentDestination
                        if (currentDestination?.id != R.id.favoriteFragment) {
                            navController.navigate(
                                R.id.favoriteFragment
                            )
                        }
                        true
                    }

                    R.id.settings -> {
                        // Navigiere zum Einstellungen-Fragment, wenn ausgewählt.
                        val currentDestination = navController.currentDestination
                        if (currentDestination?.id != R.id.settingsFragment) {
                            navController.navigate(
                                R.id.settingsFragment
                            )
                        }
                        true
                    }

                    R.id.home -> {
                        // Navigiere zum Home-Fragment, wenn ausgewählt.
                        val currentDestination = navController.currentDestination
                        if (currentDestination?.id != R.id.homeFragment) {
                            //Navigate to Favorite
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


}


