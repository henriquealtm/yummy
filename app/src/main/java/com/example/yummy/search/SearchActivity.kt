package com.example.yummy.search

import android.content.Intent
import androidx.databinding.*
import androidx.lifecycle.Observer
import com.example.widget.imagewithlabel.ImageWithLabel
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

    override fun initializeUI() {

    }

    override fun initializeViewModels() {
        searchVm.run {
            actionTextPrefix = getString(R.string.search_to_clean_prefix)

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

            filteredFieldsCount.observe(this@SearchActivity, Observer {

            })
        }
    }

}


