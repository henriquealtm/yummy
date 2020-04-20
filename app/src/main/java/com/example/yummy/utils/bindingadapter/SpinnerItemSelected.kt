package com.example.yummy.utils.bindingadapter

import android.view.View
import androidx.databinding.InverseBindingAdapter
import android.widget.ArrayAdapter
import android.widget.AdapterView
import android.widget.Spinner
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.InverseBindingListener
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseMethod

@BindingAdapter("app:itemSelected")
fun setItemSelected(
    spinner: AppCompatSpinner,
    value: Any?
) {
    value?.let {
        @Suppress("UNCHECKED_CAST")
        val pos = (spinner.adapter as ArrayAdapter<Any>).getPosition(value)
        spinner.setSelection(pos, true)
    }
}

@InverseBindingAdapter(attribute = "app:itemSelected", event = "app:itemSelectedAttrChanged")
fun getItemSelected(view: AppCompatSpinner): Any? = view.selectedItem

@BindingAdapter("app:itemSelectedAttrChanged")
fun setItemSelectedListeners(
    view: AppCompatSpinner,
    attrChange: InverseBindingListener
) {
    view.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            attrChange.onChange()
        }
    }
}