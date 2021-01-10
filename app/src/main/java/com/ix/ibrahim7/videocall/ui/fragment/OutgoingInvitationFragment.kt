package com.ix.ibrahim7.videocall.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.facebook.react.modules.core.PermissionListener
import com.ix.ibrahim7.videocall.R
import com.ix.ibrahim7.videocall.databinding.FragmentOutgoingInvitationBinding
import com.ix.ibrahim7.videocall.model.CallingData
import com.ix.ibrahim7.videocall.model.User
import com.ix.ibrahim7.videocall.network.ApiClient
import com.ix.ibrahim7.videocall.util.Constant.CALL_AUDIO
import com.ix.ibrahim7.videocall.util.Constant.CALL_VIDEO
import com.ix.ibrahim7.videocall.util.Constant.REMOTE_MSG_INVITATION
import com.ix.ibrahim7.videocall.util.Constant.REMOTE_INVITATION_CANCEL
import com.ix.ibrahim7.videocall.util.Constant.TYPE_CALL
import com.ix.ibrahim7.videocall.util.Constant.USER_DATA
import com.ix.ibrahim7.videocall.util.Constant.getUserProfile
import com.nurbk.ps.projectm.model.PushCalling
import okhttp3.ResponseBody
import org.jitsi.meet.sdk.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL
import java.util.*


class OutgoingInvitationFragment : Fragment(), JitsiMeetViewListener, JitsiMeetActivityInterface {

    private lateinit var mBinding: FragmentOutgoingInvitationBinding
    private lateinit var argumentData: Bundle
    private lateinit var user: User
    private var typeMeeting = CALL_VIDEO
    private var meetingRoom = ""
    private var isAudio = false


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
            mBinding.imageTypeCall.setImageResource(R.drawable.ic_baseline_call_24)
            typeMeeting = CALL_AUDIO
        }
        mBinding.btnCancelCall.setOnClickListener {
            sendRemoteMessage(REMOTE_INVITATION_CANCEL)
            findNavController().navigateUp()
        }
        sendRemoteMessage(REMOTE_MSG_INVITATION)

    }

    private fun sendRemoteMessage(type: String) {
        meetingRoom = userProfile.id + "_" + UUID.randomUUID().toString().substring(0, 5)

        PushCalling(
            CallingData(
                name = userProfile.name, meetingType = typeMeeting,
                type = type, email = userProfile.email,
                senderToken = userProfile.token,
                receiverToken = user.token,
                meetingRoom = meetingRoom
            ),
            user.token
        ).also {
            ApiClient(requireContext()).notificationInterface
                .sendRemoteMessage(
                    it
                ).enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful){
                            val options =
                                JitsiMeetConferenceOptions.Builder()
                                    .setServerURL(URL("https://meet.jit.si"))
                                    .setRoom(meetingRoom)
                                    .setAudioMuted(false)
                                    .setVideoMuted(false)
                                    .setAudioOnly(false)
                                    .setWelcomePageEnabled(false)
                                    .build()
                            JitsiMeetActivity.launch(requireContext(),options)
                        }
                        else {
                            Log.e("tttttttttttt", response.errorBody().toString())

                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                    }
                })
        }

    }

    override fun requestPermissions(p0: Array<out String>?, p1: Int, p2: PermissionListener?) {

    }

    override fun checkPermission(p0: String?, p1: Int, p2: Int): Int {
       return  1
    }

    override fun checkSelfPermission(p0: String?): Int {
        return  1
    }

    override fun onConferenceTerminated(p0: MutableMap<String, Any>?) {

    }

    override fun onConferenceJoined(p0: MutableMap<String, Any>?) {
    }

    override fun onConferenceWillJoin(p0: MutableMap<String, Any>?) {

    }


}