package com.xc.brainstore.view.me

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.xc.brainstore.R
import com.xc.brainstore.data.remote.response.UserDetailResponse
import com.xc.brainstore.databinding.FragmentMeBinding
import com.xc.brainstore.di.Injection
import com.xc.brainstore.view.ViewModelFactory

class MeFragment : Fragment() {
    private lateinit var binding: FragmentMeBinding
    private val repository by lazy { Injection.provideRepository(requireActivity()) }

    private val viewModel: MeViewModel by lazy {
        val factory = ViewModelFactory(repository)
        ViewModelProvider(this, factory)[MeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMeBinding.inflate(inflater, container, false)
        binding.progressBar.visibility = View.GONE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserDetail()
        viewModel.userDetail.observe(viewLifecycleOwner) { userDetail ->
            userDetail?.let {
                updateUI(userDetail)
            }
        }

        setupAction()
    }

    private fun setupAction() {
        binding.cardView.setOnClickListener {
            findNavController().navigate(R.id.action_meFragment_to_editMeFragment)
        }

        setLanguageSettingsClickListener(
            binding.languageSettingIcon,
            binding.languageSettingTextView,
            binding.langArrow
        )

        setClickListenerToOpenHelpFragment(
            binding.helpSettingIcon,
            binding.helpSettingTextView,
            binding.helpArrow
        )

        binding.cardViewLogout.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun updateUI(userDetail: UserDetailResponse) {
        Glide.with(this)
            .load(userDetail.userImg)
            .into(binding.userProfilePicture)
        binding.profileUsername.text = userDetail.userName
        binding.profileUserEmail.text = userDetail.userEmail
    }

    private fun setLanguageSettingsClickListener(vararg views: View) {
        val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        views.forEach { view ->
            view.setOnClickListener {
                startActivity(intent)
            }
        }
    }

    private fun setClickListenerToOpenHelpFragment(vararg views: View) {
        views.forEach { view ->
            view.setOnClickListener {
                findNavController().navigate(R.id.action_meFragment_to_helpCenterFragment)
            }
        }
    }
}