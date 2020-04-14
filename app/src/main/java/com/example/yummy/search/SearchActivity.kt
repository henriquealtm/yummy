package com.example.yummy.search

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.yummy.R
import com.example.yummy.RecipeListActivity
import com.example.yummy.databinding.ActivitySearchBinding
import com.example.yummy.utils.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : BaseActivity<ActivitySearchBinding>(
    R.layout.activity_search
) {

    private val searchVm: SearchViewModel by viewModel()

    override val loadVm: (ActivitySearchBinding) -> Unit = { binding ->
        binding.vm = searchVm
    }

    override fun initializeUI() {

    }

    override fun initializeViewModels() {
        searchVm.run {
            onNavigateBack.observe(this@SearchActivity, Observer { mustNavigateBack ->
                if (mustNavigateBack) {
                    this@SearchActivity.finish()
                }
            })

            onSearchRecipe.observe(this@SearchActivity, Observer { mustSearch ->
                if (mustSearch) {
                    startActivity(Intent(this@SearchActivity, RecipeListActivity::class.java))
                }
            })
        }
    }

}
