package com.example.widget.progressbutton

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.example.widget.R
import kotlinx.android.synthetic.main.progress_button.view.*

class ProgressButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var text: String? = null
        set(text) {
            field = text
            btn_progress_button.text = text
        }

    var progressButtonState: ProgressButtonState = ProgressButtonState.ENABLED
        set(value) {
            field = value
            when (value) {
                ProgressButtonState.ENABLED -> isEnabled = true
                ProgressButtonState.DISABLED -> isEnabled = false
                ProgressButtonState.LOADING -> showLoader(true)
            }
        }

    var isLoading: Boolean = false
        private set

    init {
        LayoutInflater.from(context).inflate(R.layout.progress_button, this, true)

        attrs?.let {
            context.obtainStyledAttributes(attrs, R.styleable.ProgressButton, 0, 0).apply {
                text = getString(R.styleable.ProgressButton_text)

                recycle()
            }
        }

        btn_progress_button.setOnClickListener { onClick() }

        showLoader(false)
    }

    private fun onClick() {
        if (!isEnabled || isLoading) return
        callOnClick()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        showLoader(false)
    }

    private fun showLoader(showLoader: Boolean) {
        isLoading = showLoader

        val (buttonText, loaderVisibility) = if (showLoader) {
            Pair("", View.VISIBLE)
        } else {
            Pair(text, View.GONE)
        }

        btn_progress_button.text = buttonText
        pb_progress_button.visibility = loaderVisibility
    }

}