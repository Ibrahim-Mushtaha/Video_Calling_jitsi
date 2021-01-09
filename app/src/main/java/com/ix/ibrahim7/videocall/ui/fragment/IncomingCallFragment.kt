package com.ix.ibrahim7.videocall.ui.fragment

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
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.videocall.R
import com.ix.ibrahim7.videocall.databinding.FragmentCallIncomingBinding
import com.ix.ibrahim7.videocall.network.ApiClient
import com.ix.ibrahim7.videocall.util.Constant.CALL_AUDIO
import com.ix.ibrahim7.videocall.util.Constant.REMOTE_MSG_INVITATION_ACCEPTED
import com.ix.ibrahim7.videocall.util.Constant.REMOTE_MSG_INVITATION_REJECTED
import com.ix.ibrahim7.videocall.util.Constant.USER_DATA
import com.nurbk.ps.projectm.model.CallingData
import com.nurbk.ps.projectm.model.PushCalling
import okhttp3.ResponseBody
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL

class IncomingCallFragment : Fragment() {

    private lateinit var mBinding: FragmentCallIncomingBinding
    private lateinit var dataCalling: CallingData;
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
        dataCalling = argumentData.getParcelable<CallingData>(USER_DATA)!!
        mBinding.data = dataCalling

        if (dataCalling.meetingType == CALL_AUDIO) {
            mBinding.imageTypeCall.setImageResource(R.drawable.ic_baseline_call_24)
            isAudio = true
        }

        mBinding.btnFinshCall.setOnClickListener {
            sendRemoteMessage(false, REMOTE_MSG_INVITATION_REJECTED)
            findNavController().navigateUp()
        }
        mBinding.btnStartCall.setOnClickListener {
            sendRemoteMessage(true, REMOTE_MSG_INVITATION_ACCEPTED)
        }
    }


    private fun sendRemoteMessage(acceptOrReject: Boolean, type: String) {
        PushCalling(
            CallingData(
                name = dataCalling.name, meetingType = dataCalling.meetingType,
                type = type, email = dataCalling.email,
                acceptedOrRejected = acceptOrReject,
                senderToken = dataCalling.receiverToken,
                receiverToken = dataCalling.senderToken
            ),
            dataCalling.senderToken
        ).also {
            ApiClient(requireContext()).notificationInterface
                .sendRemoteMessage(
                    it
                ).enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {

                            try {

                                val server = URL("https://meet.jit.si")
                                val options = JitsiMeetConferenceOptions.Builder()
                                    .setServerURL(server)
                                    .setRoom(dataCalling.meetingRoom)
                                    .setWelcomePageEnabled(false)
                                    .setVideoMuted(isAudio)
                                    .build()
                                JitsiMeetActivity.launch(requireContext(), options)
                                findNavController().navigateUp()

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        } else {
                            Log.e("tttttttttttt", response.errorBody().toString())

                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                    }
                })
        }

    }



}