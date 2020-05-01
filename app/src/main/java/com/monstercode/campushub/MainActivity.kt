package com.monstercode.campushub


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.navigation.NavigationView
import com.monstercode.campushub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout : DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout

        appBarConfiguration = AppBarConfiguration(setOf(R.id., R.id.creditsFragment), drawerLayout)

        // setting up navigation UI
        val navController = this.findNavController(R.id.myNavHostFragment)

        setupActionBar(navController, appBarConfiguration)

        // connect navigation drawer to navigation controller
        connectDrawerToController(binding.navView, navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return navController.navigateUp(appBarConfiguration)
    }

    private fun connectDrawerToController(navView: NavigationView, navController: NavController) {
        NavigationUI.setupWithNavController(navView, navController)
    }

    // not necessary since we have multiple top level destinations so we will use setupActionBar()
    private fun setupActionBarNavigation(navController: NavController, drawerLayout: DrawerLayout) {
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
    }

    // Have NavigationUI decide what label to show in the action bar
    // It will also determine whether to show up arrow or drawer menu icon
    // appBarConfiguration contains top level destinations
    private fun setupActionBar(navController: NavController, appBarConfiguration: AppBarConfiguration) {
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

}
