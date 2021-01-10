package com.ix.ibrahim7.videocall.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.videocall.databinding.FragmentSignUpBinding
import com.ix.ibrahim7.videocall.util.Constant.dialog
import com.ix.ibrahim7.videocall.util.Constant.showDialog
import com.ix.ibrahim7.videocall.model.User
import com.nurbk.ps.projectm.ui.viewmodel.SignUpAuthViewModel

class SignUpFragment : Fragment() {

    private lateinit var mBinding: FragmentSignUpBinding
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[SignUpAuthViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentSignUpBinding.inflate(inflater, container, false)
            .apply { executePendingBindings() }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.bntSignIn.setOnClickListener {
            findNavController().navigateUp()
        }

        mBinding.btnSinUp.setOnClickListener {

            val name = mBinding.txtName.text.toString()
            val email = mBinding.txtEmail.text.toString()
            val password = mBinding.txtPassword.text.toString()
            val confPassword = mBinding.txtConPassword.text.toString()

            showDialog(requireActivity())
            viewModel.createAccount(User(name = name, email = email, password = password))

        }

        viewModel.getSignUp().observe(viewLifecycleOwner, Observer  {
            if (it) {
                Toast.makeText(requireContext(), "True", Toast.LENGTH_LONG).show()
                findNavController().navigateUp()
            }
            dialog.dismiss()
        })

    }

}