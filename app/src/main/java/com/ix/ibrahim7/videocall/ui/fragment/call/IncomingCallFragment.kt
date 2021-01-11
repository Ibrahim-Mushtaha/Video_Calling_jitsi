package com.ix.ibrahim7.videocall.ui.fragment.call

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.videocall.R
import com.ix.ibrahim7.videocall.databinding.FragmentIncomingCallBinding
import com.ix.ibrahim7.videocall.model.NotificationData
import com.ix.ibrahim7.videocall.model.PushCalling
import com.ix.ibrahim7.videocall.util.Constant
import com.ix.ibrahim7.videocall.util.Constant.DATA
import com.ix.ibrahim7.videocall.util.Constant.MEETURL
import com.ix.ibrahim7.videocall.util.Constant.NOTIFICATION_DATE
import com.ix.ibrahim7.videocall.util.Constant.REMOTE_MSG_INVITATION_ACCEPTED
import com.ix.ibrahim7.videocall.util.Constant.REMOTE_MSG_INVITATION_CANCEL
import com.ix.ibrahim7.videocall.util.Constant.REMOTE_MSG_INVITATION_REJECTED
import com.ix.ibrahim7.videocall.util.Constant.REMOTE_MSG_INVITATION_RESPONSE
import com.ix.ibrahim7.videocall.util.Constant.TYPE
import com.ix.ibrahim7.videocall.util.Constant.USER_DATA
import com.ix.ibrahim7.videocall.util.Constant.playrRingtone
import com.ix.ibrahim7.videocall.util.Constant.stopRingtone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import java.net.URL

class IncomingCallFragment : Fragment() {

    private lateinit var mBinding: FragmentIncomingCallBinding
    private lateinit var dataNotification: NotificationData

    private val argumentData by lazy {
        requireArguments()
    }

    private var isAudio = false


    override fun onStart() {
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(
                initBroadcastManager,
                IntentFilter(REMOTE_MSG_INVITATION_RESPONSE)
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

        mBinding.btnFinshCall.setOnClickListener {
            stopRingtone()
            sendRemoteMessage(false, REMOTE_MSG_INVITATION_REJECTED)
            findNavController().navigateUp()
        }

        mBinding.btnStartCall.setOnClickListener {
            sendRemoteMessage(true, REMOTE_MSG_INVITATION_ACCEPTED)
            stopRingtone()
        }


    }


    private val initBroadcastManager = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val type = intent!!.getParcelableExtra<NotificationData>("data")
            when (type!!.type) {
                REMOTE_MSG_INVITATION_CANCEL -> {
                    stopRingtone()
                    findNavController().navigateUp()
                }
            }
        }
    }

    private fun sendRemoteMessage(acceptOrReject: Boolean, type: String) {
        PushCalling(
            NotificationData(
                name = dataNotification.name, meetingType = dataNotification.meetingType,
                type = type, email = dataNotification.email,
                senderToken = dataNotification.receiverToken,
                receiverToken = dataNotification.senderToken,
                acceptedOrRejected = acceptOrReject
            ),
            dataNotification.senderToken
        ).also {
            PushCalling().Notification().sendMessage(requireContext(), it) { msg, done ->
                if (done) {
                    try {
                            if (type == REMOTE_MSG_INVITATION_ACCEPTED) {
                                stopRingtone()
                                val options = JitsiMeetConferenceOptions.Builder()
                                    .setServerURL(URL(MEETURL))
                                    .setRoom(dataNotification.meetingRoom)
                                    .setWelcomePageEnabled(false)
                                    .setVideoMuted(isAudio)
                                    .build()
                                JitsiMeetActivity.launch(requireContext(), options)
                                findNavController().navigateUp()
                            }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    Log.e("tttttttttttt", msg!!)
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