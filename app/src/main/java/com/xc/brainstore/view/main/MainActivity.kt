package com.xc.brainstore.view.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.xc.brainstore.databinding.ActivityMainBinding
import com.xc.brainstore.di.Injection
import com.xc.brainstore.view.ViewModelFactory
import com.xc.brainstore.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val repository by lazy { Injection.provideRepository(this) }

    private val viewModel: MainViewModel by lazy {
        val factory = ViewModelFactory(repository)
        ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        observeViewModel()
    }

    private fun setupView() {
        supportActionBar?.hide()
        val navView: BottomNavigationView = binding.navView
        val navController =
            binding.navHostFragmentActivityMain.getFragment<NavHostFragment>().navController
        navView.setupWithNavController(navController)
    }

    private fun observeViewModel() {
        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                binding.root.visibility = View.VISIBLE
            }
        }
    }

}