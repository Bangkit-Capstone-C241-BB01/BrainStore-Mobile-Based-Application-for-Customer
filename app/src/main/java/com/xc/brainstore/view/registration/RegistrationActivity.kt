package com.xc.brainstore.view.registration

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.xc.brainstore.R
import com.xc.brainstore.customview.EditTextConfirmPassword
import com.xc.brainstore.customview.EditTextEmail
import com.xc.brainstore.customview.EditTextName
import com.xc.brainstore.customview.EditTextPassword
import com.xc.brainstore.data.model.UserRegistModel
import com.xc.brainstore.databinding.ActivityRegistrationBinding
import com.xc.brainstore.view.ViewModelFactory
import com.xc.brainstore.view.login.LoginActivity

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding

    private val viewModel: RegisViewModel by lazy {
        val factory = ViewModelFactory.getInstance(this)
        ViewModelProvider(this, factory)[RegisViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
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

        val editTextName: EditTextName = binding.edRegisName
        editTextName.hint = getString(R.string.name_hint)

        val editTextEmail: EditTextEmail = binding.edRegisEmail
        editTextEmail.hint = getString(R.string.email_hint)

        val editTextPassword: EditTextPassword = binding.edRegisPassword
        editTextPassword.hint = getString(R.string.password_hint)

        val editTextPasswordConfirmation: EditTextConfirmPassword = binding.edRegisPasswordConfirm
        editTextPasswordConfirmation.hint = getString(R.string.confirm_password_hint)
    }

    private fun setupAction() {
        binding.regisButton.setOnClickListener {
            val name = binding.edRegisName.text.toString()
            val email = binding.edRegisEmail.text.toString()
            val password = binding.edRegisPassword.text.toString()
            val passwordConfirmation = binding.edRegisPasswordConfirm.text.toString()
            val successMessage = getString(R.string.success_regis_msg, name)
            val userRegisModel = UserRegistModel(name, email, password, "customer")

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirmation.isEmpty()) {
                showToast(getString(R.string.empty_login_fields))
            } else {
                viewModel.getRegisterResponse(userRegisModel)

                viewModel.isLoading.observe(this) { isLoading ->
                    showLoading(isLoading)
                }

                viewModel.isRequestSuccessful.observe(this) { isSuccessful ->
                    if (isSuccessful) {
                        AlertDialog.Builder(this).apply {
                            setTitle(getString(R.string.success_regis))
                            setMessage(successMessage)
                            setPositiveButton(getString(R.string.cont)) { _, _ ->
                                finish()
                            }
                            create()
                            show()
                        }
                    } else {
                        getString(R.string.req_failed)
                    }
                }

                viewModel.message.observe(this) { message ->
                    if (message?.isNotEmpty() == true) {
                        showToast(message)
                    }
                }
            }
        }

        binding.loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
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