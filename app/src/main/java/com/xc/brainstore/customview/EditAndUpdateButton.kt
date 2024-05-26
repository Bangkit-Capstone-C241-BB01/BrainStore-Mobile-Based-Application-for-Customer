package com.xc.brainstore.customview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class EditAndUpdateButton : AppCompatButton {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

//    private var txtColor: Int = 0
//    private var enabledBackground: Drawable
//    private var disabledBackground: Drawable
//    init {
//        txtColor = ContextCompat.getColor(context, android.R.color.background_light)
//        enabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button) as Drawable
//        disabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_before) as Drawable
//    }
//
//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//        background = if(isEnabled) enabledBackground else disabledBackground
//        setTextColor(txtColor)
//        textSize = 12f
//        gravity = Gravity.CENTER
//        text = if(isEnabled) context.getString(R.string.update_data) else context.getString(R.string.edit_data)
//    }
}