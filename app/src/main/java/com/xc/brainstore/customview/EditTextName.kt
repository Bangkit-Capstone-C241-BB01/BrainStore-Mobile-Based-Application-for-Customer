package com.xc.brainstore.customview

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.textfield.TextInputLayout
import com.xc.brainstore.R
import com.xc.brainstore.utils.Validation

class EditTextName @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    private val textInputLayout by lazy {
        parent.parent as? TextInputLayout
    }

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                error = s.takeIf { it.isNotEmpty() }?.let {
                    if (!it.toString()
                            .matches(Validation.nameRegex)
                    ) context.getString(R.string.invalid_name) else null
                }
            }

            override fun afterTextChanged(s: Editable) {
                when {
                    s.toString().trim().isEmpty() -> {
                        textInputLayout?.error = context.getString(R.string.req)
                    }

                    else -> {
                        textInputLayout?.error = null
                    }
                }
            }
        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        isSingleLine = true
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }
}