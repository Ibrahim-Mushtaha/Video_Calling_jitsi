package com.ix.ibrahim7.videocall.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.ix.ibrahim7.videocall.R
import com.ix.ibrahim7.videocall.databinding.ActivityMainBinding
import com.ix.ibrahim7.videocall.ui.viewmodel.home.UserViewModel
import com.ix.ibrahim7.videocall.util.Constant.USER_PROFILE
import com.ix.ibrahim7.videocall.util.Constant.getSharePref

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
    ViewModelProvider(this)[UserViewModel::class.java]
    }

    lateinit var mBinding: ActivityMainBinding


    override fun onResume() {
        if (getSharePref(this).getString(USER_PROFILE,"")!!.isNotEmpty())
        viewModel.updateUserStatus(this,true)
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?

        val navController = navHostFragment!!.navController

        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.userListFragment,R.id.sigInFragment,R.id.signUpFragment
        ))

        setSupportActionBar(mBinding.toolbar)

        findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController,appBarConfiguration)

        navHostFragment.navController.addOnDestinationChangedListener { _: NavController?, destination: NavDestination, arguments: Bundle? ->
            when (destination.id) {
                R.id.sigInFragment, R.id.signUpFragment,R.id.outgoingCallFragment,R.id.IncomingcallFragment -> {
                    window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                    mBinding.appbar.visibility = View.GONE
                }
                else->{
                    window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                    mBinding.appbar.visibility =View.VISIBLE
                }
            }
        }
    }

    override fun onPause() {
        if (getSharePref(this).getString(USER_PROFILE,"")!!.isNotEmpty())
        viewModel.updateUserStatus(this,false)
        super.onPause()
    }

    override fun onDestroy() {
        if (getSharePref(this).getString(USER_PROFILE,"")!!.isNotEmpty())
        viewModel.updateUserStatus(this,false)
        super.onDestroy()
    }

}