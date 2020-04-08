package com.example.yummy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_receipe_list.*

class RecipeListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipe_list)

        cardView.setOnClickListener {
            startActivity(
                Intent(this, RecipeDetailsActivity::class.java)
            )
        }
    }
}
