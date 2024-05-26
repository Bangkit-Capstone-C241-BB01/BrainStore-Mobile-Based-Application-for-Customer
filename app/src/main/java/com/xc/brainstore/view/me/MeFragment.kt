package com.xc.brainstore.view.me

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.xc.brainstore.R
import com.xc.brainstore.databinding.FragmentMeBinding
import com.xc.brainstore.view.ViewModelFactory

class MeFragment : Fragment() {
    private lateinit var binding: FragmentMeBinding

    private val viewModel: MeViewModel by lazy {
        val factory = ViewModelFactory.getInstance(requireActivity())
        ViewModelProvider(requireActivity(), factory)[MeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
    }

    private fun setupAction() {
        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        binding.cardView.setOnClickListener {
            navController.navigate(R.id.action_meFragment_to_editMeFragment)
        }

        binding.cardViewLogout.setOnClickListener {
            viewModel.logout()
        }
    }
}