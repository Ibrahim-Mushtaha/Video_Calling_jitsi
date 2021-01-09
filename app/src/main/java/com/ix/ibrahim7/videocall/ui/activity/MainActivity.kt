package com.ix.ibrahim7.videocall.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.ix.ibrahim7.videocall.R
import com.ix.ibrahim7.videocall.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?

        val navController = navHostFragment!!.navController

        val appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
        NavigationUI.setupWithNavController(
            mBinding.toolbar, navController, appBarConfiguration
        )
        setSupportActionBar(mBinding.toolbar)

        navHostFragment.navController.addOnDestinationChangedListener { _: NavController?, destination: NavDestination, arguments: Bundle? ->
            when (destination.id) {
                R.id.sigInFragment, R.id.signUpFragment -> {
                    mBinding.toolbar.isVisible = false
                }
            }
        }

    }
}