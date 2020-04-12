package com.example.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.toolbar.view.*

class Toolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private var text: String? = null
        set(value) {
            field = value
            tv_title_toolbar.text = value
        }

    private var actionText: String? = null
        set(value) {
            field = value
            tv_action_toolbar.text = value
        }

    var onBackClick: (() -> Unit)? = null
        set(value) {
            field = value
            iv_toolbar.setOnClickListener { onBackClick?.invoke() }
        }

    var onActionClick: (() -> Unit)? = null
        set(value) {
            field = value
            tv_action_toolbar.setOnClickListener { onActionClick?.invoke() }
        }


    init {
        LayoutInflater.from(context).inflate(R.layout.toolbar, this, true)

        attrs?.let {
            context.obtainStyledAttributes(attrs, R.styleable.Toolbar, 0, 0).apply {
                text = getString(R.styleable.Toolbar_text)
                actionText = getString(R.styleable.Toolbar_actionText)

                recycle()
            }
        }
    }

}