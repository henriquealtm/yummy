package com.example.yummy.search.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.yummy.R
import com.example.yummy.databinding.ViewIngredientFormBinding

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
            binding.item = item
            binding.lifecycleOwner = lifecycleOwner
        }

    }
}