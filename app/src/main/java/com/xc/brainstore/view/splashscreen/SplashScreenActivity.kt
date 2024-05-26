package com.xc.brainstore.view.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.xc.brainstore.R
import com.xc.brainstore.di.Injection
import com.xc.brainstore.view.ViewModelFactory
import com.xc.brainstore.view.main.MainActivity
import com.xc.brainstore.view.main.MainViewModel
import com.xc.brainstore.view.welcome.WelcomeActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private val repository by lazy { Injection.provideRepository(this) }
    private val viewModel: MainViewModel by lazy {
        val factory = ViewModelFactory(repository)
        ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        viewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, WelcomeActivity::class.java))
            }
            finish()
        }
    }
}