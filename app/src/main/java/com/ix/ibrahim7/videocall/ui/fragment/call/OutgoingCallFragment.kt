package com.ix.ibrahim7.videocall.ui.fragment.call

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.videocall.R
import com.ix.ibrahim7.videocall.databinding.FragmentOutcomingCallBinding
import com.ix.ibrahim7.videocall.model.NotificationData
import com.ix.ibrahim7.videocall.model.User
import com.ix.ibrahim7.videocall.util.Constant.CALL_AUDIO
import com.ix.ibrahim7.videocall.util.Constant.CALL_VIDEO
import com.ix.ibrahim7.videocall.util.Constant.TYPE_CALL
import com.ix.ibrahim7.videocall.util.Constant.USER_DATA
import com.ix.ibrahim7.videocall.util.Constant.getUserProfile
import com.ix.ibrahim7.videocall.ui.viewmodel.call.CallViewModel
import com.ix.ibrahim7.videocall.util.Constant.REMOTE_MSG_INVITATION
import com.ix.ibrahim7.videocall.util.Constant.INVITATION_ACCEPTED
import com.ix.ibrahim7.videocall.util.Constant.INVITATION_CANCEL
import com.ix.ibrahim7.videocall.util.Constant.INVITATION_REJECTED
import com.ix.ibrahim7.videocall.util.Constant.INVITATION_RESPONSE
import java.util.*


class OutgoingCallFragment : Fragment() {

    private lateinit var mBinding: FragmentOutcomingCallBinding
    private lateinit var user: User
    private var meetingType = CALL_VIDEO
    private var meetingRoom = ""
    private var isAudio = false

    private val viewModel by lazy {
        ViewModelProvider(this)[CallViewModel::class.java]
    }

    private val getDetails by lazy {
        requireArguments()
    }

    private val userProfile by lazy {
        getUserProfile(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentOutcomingCallBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = getDetails.getParcelable(USER_DATA)!!
        mBinding.apply {
            txtName.text=user.name
            txtUserName.text=user.name
            txtEmail.text=user.email
        }
        meetingRoom = userProfile.id + "_" + UUID.randomUUID().toString().substring(0, 5)

        if (getDetails.getString(TYPE_CALL) == CALL_AUDIO) {
            isAudio = true
            meetingType = CALL_AUDIO
        }

        viewModel.sendRemoteMessage(
            requireContext(), NotificationData(
                name = userProfile.name, meetingType = meetingType,
                type = REMOTE_MSG_INVITATION, email = userProfile.email,
                senderToken = userProfile.token,
                receiverToken = user.token,
                meetingRoom = meetingRoom
            ), REMOTE_MSG_INVITATION, user.token
        )



        mBinding.btnCancelCall.setOnClickListener {
            viewModel.sendRemoteMessage(
                requireContext(), NotificationData(
                    name = userProfile.name, meetingType = meetingType,
                    type = INVITATION_CANCEL, email = userProfile.email,
                    senderToken = userProfile.token,
                    receiverToken = user.token,
                    meetingRoom = meetingRoom
                ), INVITATION_CANCEL, user.token
            )
            findNavController().navigateUp()
        }

    }


    private val initBroadcastManager = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val type = intent!!.getParcelableExtra<NotificationData>("data")
            when (type!!.type) {
                INVITATION_ACCEPTED -> {
                    findNavController().navigateUp()
                    viewModel.startCalling(requireContext(), meetingRoom, isAudio)
                }
                INVITATION_REJECTED -> {
                    findNavController().navigateUp()
                }
                INVITATION_CANCEL -> {
                    findNavController().navigateUp()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(
                initBroadcastManager,
                IntentFilter(INVITATION_RESPONSE)
            )
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(requireContext())
            .unregisterReceiver(initBroadcastManager)
    }


}