package com.ix.ibrahim7.videocall.ui.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.ix.ibrahim7.videocall.R
import com.ix.ibrahim7.videocall.adapter.UserListAdapter
import com.ix.ibrahim7.videocall.databinding.FragmentUserListBinding
import com.ix.ibrahim7.videocall.ui.activity.MainActivity
import com.ix.ibrahim7.videocall.util.Constant
import com.ix.ibrahim7.videocall.util.Constant.USER_DATA_PROFILE
import com.ix.ibrahim7.videocall.util.Constant.getSharePref
import com.nurbk.ps.projectm.model.CallingData
import com.nurbk.ps.projectm.model.User
import com.ix.ibrahim7.videocall.ui.viewmodel.MainUserListViewModel
import com.ix.ibrahim7.videocall.util.Constant.CALL_AUDIO
import com.ix.ibrahim7.videocall.util.Constant.CALL_VIDEO
import com.ix.ibrahim7.videocall.util.Constant.REMOTE_MSG_INVITATION
import com.ix.ibrahim7.videocall.util.Constant.REMOTE_MSG_INVITATION_RESPONSE
import com.ix.ibrahim7.videocall.util.Constant.TYPE_CALL
import com.ix.ibrahim7.videocall.util.Constant.USER_DATA
import com.ix.ibrahim7.videocall.util.Constant.showDialog


class UserListFragment : Fragment(), UserListAdapter.UserListener {

    private lateinit var mBinding: FragmentUserListBinding


    private val viewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[MainUserListViewModel::class.java]
    }

    private lateinit var user: User
    private val userAdapter by lazy {
        UserListAdapter(arrayListOf(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentUserListBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProfile(requireContext()) {
            user = Gson().fromJson(Constant.getSharePref(requireContext())!!.getString(USER_DATA_PROFILE, ""), User::class.java)
            mBinding.txtNameUSer.text = user.name
        }
        viewModel.getToken(requireContext()) {

        }




        mBinding.btnLogOut.setOnClickListener {
         showDialog(requireActivity())
            viewModel.getLogOut().also {
                getSharePref(requireContext()).edit()!!.clear().clear().apply()
                requireActivity().finish()
                startActivity(Intent(requireContext(), MainActivity::class.java))
            }
        }



        viewModel.getAllUserLiveData.observe(viewLifecycleOwner, Observer <List<User>>{
            userAdapter.apply {
                userList.clear()
                userList.addAll(it)
                notifyDataSetChanged()
            }
        })

        mBinding.rcDataList.adapter = userAdapter


//        if (requireActivity().intent.getBooleanExtra("d", false)) {
//            findNavController().navigate(
//                R.id.action_userListFragment_to_callFragment,
//                Bundle().apply {
//                    putParcelable(
//                        USER_DATA,
//                        requireActivity().intent.getParcelableExtra<CallingData>("data")
//                    )
//                    putString(TYPE_CALL, CALL_AUDIO)
//                })
//
//        }
    }


    override fun initiateVideoMeeting(user: User) {
        if (TextUtils.isEmpty(user.token)) {
            Snackbar.make(
                requireView(),
                "${user.name} is not available for meeting",
                Snackbar.LENGTH_LONG
            ).show()
        } else {
            findNavController()
                .navigate(
                    R.id.action_userListFragment_to_outgoingInvitationFragment,
                    Bundle().apply {
                        putParcelable(USER_DATA, user)
                        putString(TYPE_CALL, CALL_VIDEO)
                    })

        }
    }

    override fun initiateAudioMeeting(user: User) {
        if (TextUtils.isEmpty(user.token)) {
            Snackbar.make(
                requireView(),
                "${user.name} is not available for meeting",
                Snackbar.LENGTH_LONG
            ).show()
        } else {
            findNavController().navigate(
                R.id.action_userListFragment_to_outgoingInvitationFragment,
                Bundle().apply {
                    putParcelable(USER_DATA, user)
                    putString(TYPE_CALL, CALL_AUDIO)
                })

        }

    }


    private val invitationBroadcastManager = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val type = intent!!.getParcelableExtra<CallingData>("data")
            when (type!!.type) {
                REMOTE_MSG_INVITATION -> {
                    findNavController().navigate(
                        R.id.action_userListFragment_to_callFragment,
                        Bundle().apply {
                            putParcelable(
                                USER_DATA,
                                type
                            )
                            putString(TYPE_CALL, CALL_AUDIO)
                        })
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(
                invitationBroadcastManager,
                IntentFilter(REMOTE_MSG_INVITATION_RESPONSE)
            )
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(requireContext())
            .unregisterReceiver(invitationBroadcastManager)
    }

}