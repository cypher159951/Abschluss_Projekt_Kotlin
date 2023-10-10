package com.example.paymessage


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.paymessage.databinding.ActivityMainBinding
import com.example.paymessage.ui.NewsViewModel
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding
    private val fragmentManager: FragmentManager = supportFragmentManager
    private lateinit var currentFragment: Fragment
    private lateinit var newsViewModel: NewsViewModel


    //Funktioniert nicht 100%ig, app crasht bei bestimmten fragmente wechseln

    //zwischen den Fragmenten wechseln
//    private fun switchFragment(fragment: Fragment) {
//        fragmentManager.commit {
//            replace(R.id.fragmentContainerView, fragment)
//            setReorderingAllowed(true)
//            addToBackStack(null)
//        }
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigationView.visibility = View.GONE

        try {
            binding.bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.favorite -> {

                        val currentDestination = navController.currentDestination
                        if (currentDestination?.id != R.id.favoriteFragment) {
                            //Navigate to Favorite
                            navController.navigate(
                                R.id.favoriteFragment
                            )
                        }

                        //einmal oben eine Funktion für, da immerwieder 3x angewendet wird, das man nicht immmer alle neu
                        //schreiben muss
                        //  switchFragment(FavoriteFragment())
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
                        // switchFragment(SettingsFragment())
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
                        //  switchFragment(HomeFragment())
                        true
                    }

                    else -> {   // Standard-Navigation zu anderen Fragmenten
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


        //Pull-to-Refresh funktion aufrufen / Nachrichten aktualisieren
        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        binding.swipeRefreshLayout.setOnRefreshListener {
            Log.d("refreshtest", "refreshing")
            val delayMillis: Long = 1000
            Handler().postDelayed({
                newsViewModel.refreshNews()
                Log.d("refreshtest2", "refreshfinish")
                binding.swipeRefreshLayout.isRefreshing = false
            }, delayMillis)
        }

    }


}
