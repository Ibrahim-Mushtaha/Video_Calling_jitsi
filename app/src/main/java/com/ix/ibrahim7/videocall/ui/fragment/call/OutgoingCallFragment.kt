package com.ix.ibrahim7.videocall.ui.fragment.call

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.videocall.R
import com.ix.ibrahim7.videocall.databinding.FragmentOutgoingInvitationBinding
import com.ix.ibrahim7.videocall.model.NotificationData
import com.ix.ibrahim7.videocall.model.User
import com.ix.ibrahim7.videocall.util.Constant.CALL_AUDIO
import com.ix.ibrahim7.videocall.util.Constant.CALL_VIDEO
import com.ix.ibrahim7.videocall.util.Constant.REMOTE_MSG_INVITATION
import com.ix.ibrahim7.videocall.util.Constant.TYPE_CALL
import com.ix.ibrahim7.videocall.util.Constant.USER_DATA
import com.ix.ibrahim7.videocall.util.Constant.getUserProfile
import com.ix.ibrahim7.videocall.model.PushCalling
import org.jitsi.meet.sdk.*
import java.net.URL
import java.util.*


class OutgoingCallFragment : Fragment() {

    private lateinit var mBinding: FragmentOutgoingInvitationBinding
    private lateinit var argumentData: Bundle
    private lateinit var user: User
    private var typeMeeting = CALL_VIDEO
    private var meetingRoom = ""
    private var isAudio = true
    private var isRun = true


    private val userProfile by lazy {
        getUserProfile(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentOutgoingInvitationBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }

        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        argumentData = requireArguments()
        user = argumentData.getParcelable(USER_DATA)!!
        mBinding.user = user


        if (argumentData.getString(TYPE_CALL) == CALL_AUDIO) {
            isAudio = true
            mBinding.imageTypeCall.setImageResource(R.drawable.ic_call)
            typeMeeting = CALL_AUDIO
        }
        mBinding.btnCancelCall.setOnClickListener {
          //  sendRemoteMessage(REMOTE_INVITATION_CANCEL)
            findNavController().navigateUp()
        }

        if (isRun)
            sendRemoteMessage(REMOTE_MSG_INVITATION)

    }

    private fun sendRemoteMessage(type: String) {

        meetingRoom = userProfile.id + "_" + UUID.randomUUID().toString().substring(0, 5)

        PushCalling(
            data = NotificationData(
                name = userProfile.name, meetingType = typeMeeting,
                type = type, email = userProfile.email,
                senderToken = userProfile.token,
                receiverToken = user.token,
                meetingRoom = meetingRoom
            ),
            to = user.token
        ).also {
            PushCalling().Notification().sendNotification(requireContext(),it){msg,done->
                if (done){
                    try {
                        Log.e("eee meet join", msg!!)
                        val server = URL("https://meet.jit.si")
                        val options = JitsiMeetConferenceOptions.Builder()
                            .setServerURL(server)
                            .setRoom(meetingRoom)
                            .setWelcomePageEnabled(false)
                            .setVideoMuted(isAudio)
                            .build()
                        isRun=false
                        JitsiMeetActivity.launch(requireContext(), options)

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }else{
                    Log.e("eee fail", msg!!)
                }
            }
        }

    }


}