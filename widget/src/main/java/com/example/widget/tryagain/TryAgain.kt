package com.example.widget.tryagain

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.widget.R
import kotlinx.android.synthetic.main.try_again.view.*

class TryAgain @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var onClick: (() -> Unit)? = null
        set(value) {
            field = value
            btn_try_again.setOnClickListener { value?.invoke() }
        }

    var text: String? = null
        set(value) {
            field = value
            tv_try_again.text = value
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.try_again, this, true)

        attrs?.let {
            context.obtainStyledAttributes(
                it,
                R.styleable.TryAgain, defStyleAttr, 0
            ).apply {
                getString(R.styleable.TryAgain_text)?.let { text ->
                    this@TryAgain.text = text
                }
                recycle()
            }
        }

    }
}