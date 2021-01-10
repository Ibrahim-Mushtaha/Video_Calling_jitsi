package com.ix.ibrahim7.videocall.ui.fragment.call

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.videocall.R
import com.ix.ibrahim7.videocall.databinding.FragmentCallIncomingBinding
import com.ix.ibrahim7.videocall.util.Constant.CALL_AUDIO
import com.ix.ibrahim7.videocall.util.Constant.REMOTE_INVITATION_ACCEPTED
import com.ix.ibrahim7.videocall.util.Constant.REMOTE_INVITATION_REJECTED
import com.ix.ibrahim7.videocall.util.Constant.USER_DATA
import com.ix.ibrahim7.videocall.model.NotificationData
import com.ix.ibrahim7.videocall.model.PushCalling
import com.ix.ibrahim7.videocall.util.Constant
import com.ix.ibrahim7.videocall.util.Constant.MEETING_ROOM
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import org.jitsi.meet.sdk.JitsiMeetView
import java.net.URL

class IncomingCallFragment : Fragment() {

    private lateinit var mBinding: FragmentCallIncomingBinding
    private lateinit var dataNotification: NotificationData;
    private val argumentData by lazy {
        requireArguments()
    }
    private var isAudio = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentCallIncomingBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = requireActivity().supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?

        val navController = navHostFragment!!.navController

     //   dataNotification = argumentData.getParcelable<NotificationData>(USER_DATA)!!
      //  mBinding.data = dataNotification

       /* if (dataNotification.meetingType == CALL_AUDIO) {
            mBinding.imageTypeCall.setImageResource(R.drawable.ic_call)
            isAudio = true
        }*/

        mBinding.btnFinshCall.setOnClickListener {
            val graph = navHostFragment.navController
                .navInflater.inflate(R.navigation.nav_home)
            graph.startDestination = R.id.userListFragment
            navHostFragment.navController.graph = graph
            findNavController().navigateUp()
        }

        mBinding.btnStartCall.setOnClickListener {
            val options =
                JitsiMeetConferenceOptions.Builder()
                    .setServerURL(URL(Constant.MEETURL))
                    .setRoom(requireActivity().intent.getStringExtra(MEETING_ROOM))
                    .setAudioMuted(false)
                    .setVideoMuted(false)
                    .setAudioOnly(false)
                    .setWelcomePageEnabled(false)
                    .build()
            JitsiMeetActivity.launch(requireContext(),options).also {
                val graph = navHostFragment.navController
                    .navInflater.inflate(R.navigation.nav_home)
                graph.startDestination = R.id.userListFragment
                navHostFragment.navController.graph = graph
            }
            //sendRemoteMessage(true, REMOTE_INVITATION_ACCEPTED)
        }


    }


    private fun sendRemoteMessage(acceptOrReject: Boolean, type: String) {
        PushCalling(
            NotificationData(
                name = dataNotification.name, meetingType = dataNotification.meetingType,
                type = type, email = dataNotification.email,
                acceptedOrRejected = acceptOrReject,
                senderToken = dataNotification.receiverToken,
                receiverToken = dataNotification.senderToken
            ),
            dataNotification.senderToken
        ).also {
            PushCalling().Notification().sendNotification(requireContext(),it){msg,done->
                if (done){
                    try {

                        val server = URL("https://meet.jit.si")
                        val options = JitsiMeetConferenceOptions.Builder()
                            .setServerURL(server)
                            .setRoom(dataNotification.meetingRoom)
                            .setWelcomePageEnabled(false)
                            .setVideoMuted(isAudio)
                            .build()
                        JitsiMeetActivity.launch(requireContext(), options)
                        findNavController().navigateUp()

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }else{
                    Log.e("tttttttttttt", msg!!)
                }
            }
        }

    }

    inner class jess(context: Context) :JitsiMeetView(context){
        override fun leave() {
            super.leave()
        }

    }


}