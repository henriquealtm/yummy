package com.example.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.loader.view.*

class Loader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var message: String? = null
        set(value) {
            field = value
            tv_loader.text = value
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.loader, this, true)

        attrs?.let {
            context.obtainStyledAttributes(it, R.styleable.Loader, defStyleAttr, 0).apply {
                getString(R.styleable.Loader_text)?.let { text ->
                    message = text
                }
                recycle()
            }
        }
    }
}