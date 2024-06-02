package com.xc.brainstore.view.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.xc.brainstore.R
import com.xc.brainstore.data.model.UserLoginModel
import com.xc.brainstore.data.model.UserModel
import com.xc.brainstore.databinding.ActivityLoginBinding
import com.xc.brainstore.view.ViewModelFactory
import com.xc.brainstore.view.main.MainActivity
import com.xc.brainstore.view.registration.RegistrationActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by lazy {
        val factory = ViewModelFactory.getInstance(this)
        ViewModelProvider(this, factory)[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()

        binding.progressBar.visibility = View.GONE

        val editTextEmail: EditText = binding.edLoginEmail
        editTextEmail.hint = getString(R.string.email_hint)

        val editTextPassword: TextInputEditText = binding.edLoginPassword
        editTextPassword.hint = getString(R.string.password_hint)
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()
            val userLoginModel = UserLoginModel(email, password)

            if (email.isEmpty() || password.isEmpty()) {
                showToast(getString(R.string.empty_login_fields))
            } else {
                viewModel.getLoginResponse(userLoginModel)

                viewModel.isLoading.observe(this) { isLoading ->
                    showLoading(isLoading)
                }

                viewModel.tokenUser.observe(this) { tokenUser ->
                    if (tokenUser?.isNotEmpty() == true) {
                        viewModel.saveSession(UserModel(email, tokenUser))
                    }
                }

                viewModel.message.observe(this) { message ->
                    if (message?.isNotEmpty() == true) {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                }

                viewModel.isRequestSuccessful.observe(this) { isSuccessful ->
                    if (isSuccessful) {
                        AlertDialog.Builder(this).apply {
                            setTitle(getString(R.string.success_login))
                            setMessage(getString(R.string.welcome_back))
                            setPositiveButton(getString(R.string.cont)) { _, _ ->
                                val intent = Intent(context, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                        }
                    } else {
                        getString(R.string.req_failed)
                    }
                }
            }
        }

        binding.regisButton.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.prevImgView.setOnClickListener {
            finish()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}