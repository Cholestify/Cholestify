package com.example.cholestifyapp

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.cholestifyapp.databinding.ActivityMainBinding
import com.example.cholestifyapp.utils.SharedPrefsHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPrefsHelper: SharedPrefsHelper
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SharedPreferences helper
        sharedPrefsHelper = SharedPrefsHelper(this)

        // Find the NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController

        // Periksa apakah token ada untuk memutuskan apakah pengguna sudah login
        val token = sharedPrefsHelper.getToken()

        // Tentukan graf navigasi berdasarkan status login
        if (token.isNullOrEmpty()) {
            // Jika token tidak ada atau kosong, navigasi ke layar login
            navController.setGraph(R.navigation.auth_nav_graph) // Gunakan navigasi login
        } else {
            // Jika token ada, artinya pengguna sudah login, navigasi ke layar utama
            navController.setGraph(R.navigation.main_nav_graph) // Gunakan navigasi utama
        }

        // Set up the BottomNavigationView with the NavController
        val navView: BottomNavigationView = binding.navView
        navView.setupWithNavController(navController)

        // Memantau perubahan tujuan navigasi
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment, R.id.registerFragment -> {
                    // Sembunyikan BottomNavigationView saat berada di login atau register
                    binding.navView.visibility = View.GONE
                }
                else -> {
                    // Tampilkan BottomNavigationView di halaman lain
                    binding.navView.visibility = View.VISIBLE
                }
            }
        }

        // Optional: Set up the AppBar with navigation
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.fragment_profile
            )
        )
    }
}