package com.ix.ibrahim7.videocall.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.ix.ibrahim7.videocall.R
import com.ix.ibrahim7.videocall.databinding.ActivityMainBinding
import com.ix.ibrahim7.videocall.util.Constant.MEETING_ROOM
import com.ix.ibrahim7.videocall.util.Constant.MEETURL
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import java.net.URL

class MainActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityMainBinding

    var run = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?

        val navController = navHostFragment!!.navController

        if (run)
        if (intent.hasExtra("call"))
            if (intent.extras!!.getInt("call",0) == 1){
                val bundle = Bundle().apply {
                    putString(MEETING_ROOM,intent.getStringExtra(MEETING_ROOM))
                }
                val graph = navHostFragment.navController
                    .navInflater.inflate(R.navigation.nav_home)
                graph.startDestination = R.id.callFragment
                navHostFragment.arguments = bundle
                navHostFragment.navController.graph = graph

                intent.extras!!.remove("call")
                run = false
            }



        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.userListFragment,R.id.sigInFragment,R.id.signUpFragment
        ))

        setSupportActionBar(mBinding.toolbar)

        findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController,appBarConfiguration)

        navHostFragment.navController.addOnDestinationChangedListener { _: NavController?, destination: NavDestination, arguments: Bundle? ->
            when (destination.id) {
                R.id.sigInFragment, R.id.signUpFragment,R.id.outgoingInvitationFragment,R.id.callFragment -> {
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
}