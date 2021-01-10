package com.ix.ibrahim7.videocall.ui.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ix.ibrahim7.videocall.R
import com.ix.ibrahim7.videocall.adapter.UserListAdapter
import com.ix.ibrahim7.videocall.databinding.FragmentUserListBinding
import com.ix.ibrahim7.videocall.ui.activity.MainActivity
import com.ix.ibrahim7.videocall.model.CallingData
import com.ix.ibrahim7.videocall.model.User
import com.ix.ibrahim7.videocall.ui.viewmodel.MainUserListViewModel
import com.ix.ibrahim7.videocall.util.Constant.CALL_AUDIO
import com.ix.ibrahim7.videocall.util.Constant.CALL_VIDEO
import com.ix.ibrahim7.videocall.util.Constant.DATA
import com.ix.ibrahim7.videocall.util.Constant.REMOTE_MSG_INVITATION
import com.ix.ibrahim7.videocall.util.Constant.REMOTE_INVITATION_RESPONSE
import com.ix.ibrahim7.videocall.util.Constant.TYPE_CALL
import com.ix.ibrahim7.videocall.util.Constant.USER_DATA
import com.ix.ibrahim7.videocall.util.Constant.editor
import com.ix.ibrahim7.videocall.util.Constant.getUserProfile


class UserListFragment : Fragment(), UserListAdapter.onClick {

    private lateinit var mBinding: FragmentUserListBinding


    private val viewModel by lazy {
        ViewModelProvider(
            requireActivity()
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
        setHasOptionsMenu(true)
        mBinding = FragmentUserListBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProfile(requireContext()) {
            user = getUserProfile(requireContext())
        //    mBinding.txtNameUSer.text = user.name
        }
        viewModel.getToken(requireContext()) {

        }



        viewModel.getAllUserLiveData.observe(viewLifecycleOwner, Observer<List<User>> {
            userAdapter.apply {
                userList.clear()
                userList.addAll(it)
                notifyDataSetChanged()
            }
        })

        mBinding.userList.adapter = userAdapter

    }


    override fun startVideoMeeting(user: User) {
        when{
            TextUtils.isEmpty(user.token)->{
                Snackbar.make(
                    requireView(),
                    "${user.name} is not available for meeting",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            else->{
                findNavController()
                    .navigate(
                        R.id.action_userListFragment_to_outgoingInvitationFragment,
                        Bundle().apply {
                            putParcelable(USER_DATA, user)
                            putString(TYPE_CALL, CALL_VIDEO)
                        })
            }
        }
    }

    override fun startVoiceMeeting(user: User) {
        when {
            TextUtils.isEmpty(user.token) -> {
                Snackbar.make(
                    requireView(),
                    "${user.name} is not available for meeting",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            else -> {
                findNavController().navigate(
                    R.id.action_userListFragment_to_outgoingInvitationFragment,
                    Bundle().apply {
                        putParcelable(USER_DATA, user)
                        putString(TYPE_CALL, CALL_AUDIO)
                    })
            }
        }
    }


    private val invitationBroadcastManager = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val type = intent!!.getParcelableExtra<CallingData>(DATA)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout->{
                viewModel.getLogOut().also {
                    editor(requireContext())!!.clear().clear().apply()
                    requireActivity().finish()
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(
                invitationBroadcastManager,
                IntentFilter(REMOTE_INVITATION_RESPONSE)
            )
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(requireContext())
            .unregisterReceiver(invitationBroadcastManager)
    }

}