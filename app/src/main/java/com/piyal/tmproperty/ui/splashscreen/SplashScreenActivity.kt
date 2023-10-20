package com.piyal.tmproperty.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import com.piyal.tmproperty.R
import com.piyal.tmproperty.ui.NavigationActivity
import com.piyal.tmproperty.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var viewModel: SplashScreenViewModel
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        viewModel = ViewModelProvider(this)[SplashScreenViewModel::class.java]
        handler = Handler()

        viewModel.isLoggedIn.observe(this) { isLoggedIn ->
            handler.postDelayed({
                val intent = if (isLoggedIn) {
                    Intent(this, NavigationActivity::class.java)
                } else {
                    Intent(this, LoginActivity::class.java)
                }
                startActivity(intent)
                finish()
            }, 1500)
        }

        viewModel.checkLoginStatus(applicationContext)
    }
}
