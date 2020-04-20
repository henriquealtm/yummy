package com.example.yummy.search.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.yummy.databinding.ViewIngredientFormBinding
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.core_ui.utils.hideKeyboard
import com.example.yummy.R

typealias aLayout = android.R.layout

class IngredientAdapter(
    private val list: MutableList<FoodIngredient>,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: ViewIngredientFormBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.view_ingredient_form,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun getItemCount() = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.populateSimulationItemOption(list[position], lifecycleOwner)
    }

    inner class ViewHolder(
        private val binding: ViewIngredientFormBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun populateSimulationItemOption(
            item: FoodIngredient,
            lifecycleOwner: LifecycleOwner
        ) {
            val amountTypeStringList = AmountType.values().map {
                context.getString(it.stringId)
            }.toList()

            ArrayAdapter<String>(
                context,
                android.R.layout.simple_spinner_item,
                amountTypeStringList
            ).run {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spIngredientForm.adapter = this
            }

            binding.item = item
            binding.lifecycleOwner = lifecycleOwner
        }

    }
}