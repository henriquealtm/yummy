package com.example.yummy.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.yummy.R
import com.example.yummy.search.presentation.SearchActivity

class SplashActivity : AppCompatActivity() {

    private val splashTime = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(
            {
                startActivity(Intent(this, SearchActivity::class.java))
                finish()
            },
            splashTime
        )

    }

}
