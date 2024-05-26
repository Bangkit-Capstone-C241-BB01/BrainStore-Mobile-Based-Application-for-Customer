package com.xc.brainstore.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.xc.brainstore.R
import com.xc.brainstore.utils.Validation

class EditTextPassword @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {

    private val passwordTextInputLayout by lazy {
        parent.parent as? TextInputLayout
    }

    private var preText: String = ""

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().trim().isEmpty() && s.toString() != preText) {
                    passwordTextInputLayout?.error = context.getString(R.string.req)
                } else {
                    passwordTextInputLayout?.error = null
                    if (s.toString().trim().length < 8 && s.toString().isNotEmpty()) {
                        passwordTextInputLayout?.error =
                            context.getString(R.string.invalid_password)
                    } else if (s.toString().isNotEmpty()) {
                        validatePasswordStrength(s.toString().trim())
                    } else {
                        passwordTextInputLayout?.helperText = null
                    }
                }
                preText = s.toString()
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing
            }
        })
    }

    private fun validatePasswordStrength(password: String) {
        val isStrong = password.matches(Validation.passRegex)
        passwordTextInputLayout?.helperText =
            if (isStrong) context.getString(R.string.strong_password) else context.getString(
                R.string.weak_password
            )
        passwordTextInputLayout?.setHelperTextColor(
            if (isStrong) ContextCompat.getColorStateList(context, R.color.green)
            else ContextCompat.getColorStateList(context, R.color.orange)
        )
    }
}