package com.example.cholestifyapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.cholestifyapp.databinding.ActivityMainBinding
import com.example.cholestifyapp.utils.SharedPrefsHelper
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPrefsHelper: SharedPrefsHelper
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate layout with ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SharedPreferences helper
        sharedPrefsHelper = SharedPrefsHelper(this)

        // Setup NavHostFragment and NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Configure the navigation graph dynamically based on login status
        configureNavigationGraph()

        // Set up BottomNavigationView with NavController
        setupBottomNavigationView()

        // Set up AppBar with NavController (optional)
        setupAppBarConfiguration()
    }

    private fun configureNavigationGraph() {
        val token = sharedPrefsHelper.getToken()
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)

        // Atur startDestination secara manual
        navGraph.setStartDestination(
            if (token.isNullOrEmpty()) {
                R.id.loginFragment
            } else {
                R.id.homeFragment
            }
        )
        navController.graph = navGraph
    }

    private fun setupBottomNavigationView() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setupWithNavController(navController)  // Gunakan navController yang sudah ada

        // Mengatur visibilitas BottomNavigationView berdasarkan fragment yang dipilih
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.navView.visibility = when (destination.id) {
                R.id.loginFragment, R.id.registerFragment -> View.GONE
                else -> View.VISIBLE
            }
        }
    }

    private fun setupAppBarConfiguration() {
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.profileFragment)
        )
        // If you want to link AppBar with navigation, you can use:
        // setupActionBarWithNavController(navController, appBarConfiguration)
    }
}