package com.example.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.core_ui.extension.handleOptional
import kotlinx.android.synthetic.main.image_with_label.view.*

class ImageWithLabel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private var text: String? = null
        set(value) {
            field = value
            tv_image_with_label.text = text
        }

    private var image: Int? = null
        set(value) {
            field = value
            value?.let {
                iv_image_with_label.setImageResource(it)
            }
        }

    private val mIsSelected = MutableLiveData(false)
    val isSelected: LiveData<Boolean>
        get() = mIsSelected


    init {
        LayoutInflater.from(context).inflate(R.layout.image_with_label, this, true)

        attrs?.let {
            context.obtainStyledAttributes(attrs, R.styleable.ImageWithLabel, 0, 0).apply {
                text = getString(R.styleable.ImageWithLabel_text)
                image = getResourceId(R.styleable.ImageWithLabel_image, 0)

                recycle()
            }
        }

        ll_image_with_label.setOnClickListener {
            mIsSelected.value = mIsSelected.value?.not()

            val color = ContextCompat.getColor(
                context, if (mIsSelected.value.handleOptional()) {
                    R.color.colorPrimary
                } else {
                    R.color.gray
                }
            )

            tv_image_with_label.setTextColor(color)
            iv_image_with_label.setColorFilter(color)
        }

    }

}