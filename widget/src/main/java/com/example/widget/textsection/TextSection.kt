package com.example.widget.textsection

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.widget.R
import kotlinx.android.synthetic.main.text_section.view.*

class TextSection @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private var text: String? = null
        set(value) {
            field = value
            tv_text_sextion.text = text
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.text_section, this, true)

        attrs?.let {
            context.obtainStyledAttributes(attrs, R.styleable.TextSection, 0, 0).apply {
                text = getString(R.styleable.TextSection_text)

                recycle()
            }
        }
    }

}