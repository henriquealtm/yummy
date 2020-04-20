package com.example.yummy.search.presentation

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core_ui.utils.hideKeyboard
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
        adapter = IngredientAdapter(searchVm.foodIngredientList.value!!, this)

        rv_search_ingredient_form.run {
            adapter = this@SearchActivity.adapter
            layoutManager = LinearLayoutManager(this@SearchActivity)
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

            showCannotAddIngredientMessage.observe(owner, Observer { showMessage ->
                if (showMessage) {
                    Toast.makeText(
                        owner,
                        R.string.cannot_add_new_ingredient,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

            foodIngredientList.observe(owner, Observer { list ->
                if (list.isNotEmpty()) {
                    rv_search_ingredient_form.adapter = IngredientAdapter(list, owner)
                }
            })

            onSearchRecipe.observe(owner, Observer { mustSearch ->
                if (mustSearch) {
                    startActivity(
                        Intent(owner, RecipeListActivity::class.java)
                    )
                }
            })
        }
    }

}