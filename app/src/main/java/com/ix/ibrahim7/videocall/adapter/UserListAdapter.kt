package com.ix.ibrahim7.videocall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ix.ibrahim7.videocall.R
import com.ix.ibrahim7.videocall.databinding.ItemUserListBinding
import com.nurbk.ps.projectm.model.User

class UserListAdapter(val userList: ArrayList<User>, val userListener: UserListener) :
    RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    inner class UserListViewHolder(val mBinding: ItemUserListBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bind(user: User) {
            mBinding.user = user
            mBinding.btnCallAudio.setOnClickListener {
                userListener.initiateAudioMeeting(user)
            }
            mBinding.btnCallvideo.setOnClickListener {
                userListener.initiateVideoMeeting(user)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        return UserListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_user_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(user = userList[position])
    }

    override fun getItemCount() = userList.size

    interface UserListener {
        fun initiateVideoMeeting(user: User)
        fun initiateAudioMeeting(user: User)
    }
}