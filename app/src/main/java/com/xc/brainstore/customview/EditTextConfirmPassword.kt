package com.xc.brainstore.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.textfield.TextInputLayout
import com.xc.brainstore.R

class EditTextConfirmPassword @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    private var preText: String = ""
    private var passwordInputEditText: EditText? = null


    private val confirmationPasswordTextInputLayout by lazy {
        parent.parent as? TextInputLayout
    }

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val password = passwordInputEditText?.text.toString().trim()
                val passwordConfirmation = s.toString().trim()

                if (passwordConfirmation.isEmpty() && passwordConfirmation != preText) {
                    confirmationPasswordTextInputLayout?.error = context.getString(R.string.req)
                } else {
                    confirmationPasswordTextInputLayout?.error = null
                    if (passwordConfirmation.isNotEmpty() && password.isNotEmpty()) {
                        if (passwordConfirmation != password) {
                            confirmationPasswordTextInputLayout?.error =
                                context.getString(R.string.invalid_confirmation_password)
                        } else {
                            confirmationPasswordTextInputLayout?.error = null
                        }
                    }
                }
                preText = s.toString()
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing
            }
        })

        post {
            passwordInputEditText = findPasswordInputEditText()
        }

    }

    private fun findPasswordInputEditText(): EditText? {
        val rootView = rootView as? ViewGroup ?: return null
        return findEditTextInChildren(rootView, R.id.ed_regis_password)
    }

    private fun findEditTextInChildren(viewGroup: ViewGroup, editTextId: Int): EditText? {
        for (i in 0 until viewGroup.childCount) {
            val child = viewGroup.getChildAt(i)
            if (child is ViewGroup) {
                val editText = child.findViewById<EditText>(editTextId)
                if (editText != null) return editText
                findEditTextInChildren(child, editTextId)
            }
        }
        return null
    }
}