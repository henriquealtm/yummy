package com.example.yummy.utils.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.example.widget.imagewithlabel.ImageWithLabel

@BindingAdapter("app:selected")
fun setSelected(
    view: ImageWithLabel,
    newValue: Boolean
) {
    if (view.selected.value != newValue) {
        view.selected.value = newValue
    }
}

@InverseBindingAdapter(attribute = "app:selected", event = "app:selectedAttrChanged")
fun getSelected(view: ImageWithLabel): Boolean? = view.selected.value

@BindingAdapter("app:selectedAttrChanged")
fun setListeners(
    view: ImageWithLabel,
    attrChange: InverseBindingListener
) {
    view.setOnClickListener { attrChange.onChange() }
}