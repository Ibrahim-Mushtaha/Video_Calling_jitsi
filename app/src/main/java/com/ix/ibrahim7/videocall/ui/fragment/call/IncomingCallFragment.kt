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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.videocall.databinding.FragmentIncomingCallBinding
import com.ix.ibrahim7.videocall.model.NotificationData
import com.ix.ibrahim7.videocall.ui.viewmodel.call.CallViewModel
import com.ix.ibrahim7.videocall.util.Constant.INVITATION_ACCEPTED
import com.ix.ibrahim7.videocall.util.Constant.INVITATION_CANCEL
import com.ix.ibrahim7.videocall.util.Constant.INVITATION_REJECTED
import com.ix.ibrahim7.videocall.util.Constant.INVITATION_RESPONSE
import com.ix.ibrahim7.videocall.util.Constant.TYPE
import com.ix.ibrahim7.videocall.util.Constant.USER_DATA
import com.ix.ibrahim7.videocall.util.Constant.playrRingtone
import com.ix.ibrahim7.videocall.util.Constant.stopRingtone

class IncomingCallFragment : Fragment() {

    private lateinit var mBinding: FragmentIncomingCallBinding
    private lateinit var dataNotification: NotificationData
    private var isAudio = false

    private val argumentData by lazy {
        requireArguments()
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[CallViewModel::class.java]
    }


    override fun onStart() {
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(
                initBroadcastManager,
                IntentFilter(INVITATION_RESPONSE)
            )
        playrRingtone(requireActivity())
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentIncomingCallBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataNotification = argumentData.getParcelable(USER_DATA)!!
        mBinding.data = dataNotification

        isAudio = requireActivity().intent.getBooleanExtra(TYPE, false)

        viewModel.CallLivedata.observe(viewLifecycleOwner, Observer {
            if (it)
                stopRingtone()
            viewModel.startCalling(requireContext(),dataNotification.meetingRoom,isAudio)
            findNavController().navigateUp()
        })

        mBinding.btnFinshCall.setOnClickListener {
            stopRingtone()
            viewModel.sendRemoteMessage(requireContext(), NotificationData(
                name = dataNotification.name, meetingType = dataNotification.meetingType,
                type = INVITATION_REJECTED, email = dataNotification.email,
                senderToken = dataNotification.receiverToken,
                receiverToken = dataNotification.senderToken,
                acceptedOrRejected = false
            ),
                INVITATION_REJECTED,dataNotification.senderToken)
            findNavController().navigateUp()
        }

        mBinding.btnStartCall.setOnClickListener {
            viewModel.sendRemoteMessage(requireContext(), NotificationData(
                name = dataNotification.name, meetingType = dataNotification.meetingType,
                type = INVITATION_ACCEPTED, email = dataNotification.email,
                senderToken = dataNotification.receiverToken,
                receiverToken = dataNotification.senderToken,
                acceptedOrRejected = true
            ),
                INVITATION_ACCEPTED,dataNotification.senderToken)
            stopRingtone()
        }


    }


    private val initBroadcastManager = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val type = intent!!.getParcelableExtra<NotificationData>("data")
            when (type!!.type) {
                INVITATION_CANCEL -> {
                    stopRingtone()
                    findNavController().navigateUp()
                }
            }
        }
    }



    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(requireContext())
            .unregisterReceiver(initBroadcastManager)
    }


}