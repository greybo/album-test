package com.greybo.testfacebook.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.greybo.testfacebook.R
import com.greybo.testfacebook.auth.AuthActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        nextActivity()
    }

    fun nextActivity() {
        AuthActivity.start(this)
        finish()
    }
}
