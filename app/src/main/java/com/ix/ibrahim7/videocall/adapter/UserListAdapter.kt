package com.ix.ibrahim7.videocall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ix.ibrahim7.videocall.R
import com.ix.ibrahim7.videocall.databinding.ItemUserBinding
import com.ix.ibrahim7.videocall.model.User

class UserListAdapter(val userList: ArrayList<User>, val itemclick: onClick) :
    RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    inner class UserViewHolder(val mBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bind(user: User) {
            mBinding.apply {
                this.user = user

                btnCallAudio.setOnClickListener {
                    itemclick.startVoiceMeeting(user)
                }

                btnCallvideo.setOnClickListener {
                    itemclick.startVideoMeeting(user)
                }

            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_user,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount() = userList.size

    interface onClick {
        fun startVideoMeeting(user: User)
        fun startVoiceMeeting(user: User)
    }
}