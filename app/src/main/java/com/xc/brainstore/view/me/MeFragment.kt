package com.xc.brainstore.view.me

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.xc.brainstore.R
import com.xc.brainstore.databinding.FragmentMeBinding
import com.xc.brainstore.di.Injection
import com.xc.brainstore.view.ViewModelFactory
import com.xc.brainstore.view.main.MainViewModel

class MeFragment : Fragment() {
    private lateinit var binding: FragmentMeBinding
    private val repository by lazy { Injection.provideRepository(requireActivity()) }

    private val mainViewModel: MainViewModel by lazy {
        val factory = ViewModelFactory(repository)
        ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    private val viewModel: MeViewModel by lazy {
        val factory = ViewModelFactory.getInstance(requireActivity())
        ViewModelProvider(requireActivity(), factory)[MeViewModel::class.java]
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

        mainViewModel.getSession().observe(requireActivity()) { user ->
            binding.profileUserEmail.text = user.email
        }

        setupAction()
    }

    private fun setupAction() {
        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        binding.cardView.setOnClickListener {
            navController.navigate(R.id.action_meFragment_to_editMeFragment)
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

    private fun setLanguageSettingsClickListener(vararg views: View) {
        val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        views.forEach { view ->
            view.setOnClickListener {
                startActivity(intent)
            }
        }
    }

    private fun setClickListenerToOpenHelpFragment(vararg views: View) {
        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        views.forEach { view ->
            view.setOnClickListener {
                navController.navigate(R.id.action_meFragment_to_helpCenterFragment)
            }
        }
    }
}