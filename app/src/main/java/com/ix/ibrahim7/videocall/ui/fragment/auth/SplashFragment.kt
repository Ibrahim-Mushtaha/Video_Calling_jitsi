package com.ix.ibrahim7.videocall.ui.fragment.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.videocall.R
import com.ix.ibrahim7.videocall.databinding.FragmentSplashBinding
import com.ix.ibrahim7.videocall.ui.viewmodel.auth.SplashState
import com.ix.ibrahim7.videocall.ui.viewmodel.auth.SplashViewModel
import com.ix.ibrahim7.videocall.util.Constant


class SplashFragment : Fragment() {

    private lateinit var mBinding:FragmentSplashBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[SplashViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      mBinding = FragmentSplashBinding.inflate(inflater,container,false).apply {
          executePendingBindings()
      }
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.liveData.observe(requireActivity(), Observer {
            when (it) {
                is SplashState.MainActivity -> {
                    val isSign = Constant.getSharePref(requireContext()).getBoolean(Constant.SIGNIN, false)
                    if (isSign)
                        findNavController().navigate(R.id.action_splashFragment_to_userListFragment)
                    else
                    findNavController().navigate(R.id.action_splashFragment_to_sigInFragment)
                }
            }
        })

        val a: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)
        a.reset()

        mBinding.apply {
            imgSplashLogo.clearAnimation()
            imgSplashLogo.startAnimation(a)
        }

        super.onViewCreated(view, savedInstanceState)
    }
}