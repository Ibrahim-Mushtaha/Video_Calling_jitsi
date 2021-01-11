package com.ix.ibrahim7.videocall.ui.fragment.call

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.videocall.R
import com.ix.ibrahim7.videocall.databinding.FragmentOutcomingCallBinding
import com.ix.ibrahim7.videocall.model.NotificationData
import com.ix.ibrahim7.videocall.model.User
import com.ix.ibrahim7.videocall.util.Constant.CALL_AUDIO
import com.ix.ibrahim7.videocall.util.Constant.CALL_VIDEO
import com.ix.ibrahim7.videocall.util.Constant.REMOTE_MSG_INVITATION
import com.ix.ibrahim7.videocall.util.Constant.TYPE_CALL
import com.ix.ibrahim7.videocall.util.Constant.USER_DATA
import com.ix.ibrahim7.videocall.util.Constant.getUserProfile
import com.ix.ibrahim7.videocall.model.PushCalling
import com.ix.ibrahim7.videocall.util.Constant.MEETURL
import kotlinx.coroutines.*
import org.jitsi.meet.sdk.*
import java.net.URL
import java.util.*


class OutgoingCallFragment : Fragment() {

    private lateinit var mBinding: FragmentOutcomingCallBinding
    private lateinit var user: User
    private var meetingType = CALL_VIDEO
    private var meetingRoom = ""
    private var isAudio = true
    private var isRun = true

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
        mBinding.user = user


        if (getDetails.getString(TYPE_CALL) == CALL_AUDIO) {
            isAudio = true
            mBinding.imageTypeCall.setImageResource(R.drawable.ic_call)
            meetingType = CALL_AUDIO
            sendRemoteMessage(CALL_AUDIO)
        } else
            if (isRun)
                sendRemoteMessage(CALL_VIDEO)


        mBinding.btnCancelCall.setOnClickListener {
            //  sendRemoteMessage(REMOTE_INVITATION_CANCEL)
            findNavController().navigateUp()
        }


    }

    private fun sendRemoteMessage(type: String) {
        meetingRoom = userProfile.id + "_" + UUID.randomUUID().toString().substring(0, 5)

        PushCalling(
            data = NotificationData(
                name = userProfile.name, meetingType = meetingType,
                type = type, email = userProfile.email,
                senderToken = userProfile.token,
                receiverToken = user.token,
                meetingRoom = meetingRoom
            ),
            to = user.token
        ).also {
            PushCalling().Notification().sendNotification(requireContext(), it) { msg, done ->
                if (done) {
                    try {
                        Log.e("eee meet join", msg!!)
                            val options = JitsiMeetConferenceOptions.Builder()
                                .setServerURL(URL(MEETURL))
                                .setRoom(meetingRoom)
                                .setWelcomePageEnabled(false)
                                .setVideoMuted(!isAudio)
                                .build()
                            isRun = false
                            JitsiMeetActivity.launch(requireContext(), options)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    Log.e("eee fail", msg!!)
                }
            }
        }

    }


}