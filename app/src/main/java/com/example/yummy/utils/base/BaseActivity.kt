package com.example.yummy.utils.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VDB : ViewDataBinding>(
    @LayoutRes private val layoutId: Int
) : AppCompatActivity() {

    abstract val loadVm: (VDB) -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (DataBindingUtil.setContentView(this, layoutId) as? VDB)?.apply {
            lifecycleOwner = this@BaseActivity
            loadVm(this)
        }

        initializeUI()
        initializeViewModels()
    }

    abstract fun initializeUI()

    abstract fun initializeViewModels()

}