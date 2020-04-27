package com.example.yummy.search.presentation

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core_ui.utils.hideKeyboard
import com.example.network.Resource
import com.example.yummy.R
import com.example.yummy.RecipeListActivity
import com.example.yummy.databinding.ActivitySearchBinding
import com.example.yummy.utils.base.BaseActivity
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : BaseActivity<ActivitySearchBinding>(
    R.layout.activity_search
) {

    private val searchVm: SearchViewModel by viewModel()

    override val loadVm: (ActivitySearchBinding) -> Unit = { binding ->
        binding.vm = searchVm
    }

    private lateinit var adapter: IngredientAdapter

    override fun initializeUI() {
        searchVm.ingredientUpdatedList.value?.let { list ->
            adapter = IngredientAdapter(list, this)

            rv_search_ingredient_form.run {
                adapter = this@SearchActivity.adapter
                layoutManager = LinearLayoutManager(this@SearchActivity)
            }
        }

        tb_search.setOnClickListener {
            it.hideKeyboard()
        }

        ll_scroll_search.setOnClickListener {
            it.hideKeyboard()
        }
    }

    override fun initializeViewModels() {
        searchVm.run {
            val owner = this@SearchActivity

            actionTextPrefix = getString(R.string.search_to_clean_prefix)

            onNavigateBack.observe(owner, Observer { mustNavigateBack ->
                if (mustNavigateBack) {
                    owner.finish()
                }
            })

            ingredientUpdatedList.observe(owner, Observer { list ->
                if (list.isNotEmpty()) {
                    rv_search_ingredient_form.adapter = IngredientAdapter(list, owner)
                }
            })

            recipeResult.observe(owner, Observer {
                // TODO Henrique - remover esse teste e passar a retornar para a act apenas a lista de fato
                it?.takeIf { it !is Resource.Loading }?.let {
                    startActivity(
                        Intent(owner, RecipeListActivity::class.java)
                    )
                }
            })
        }
    }

}