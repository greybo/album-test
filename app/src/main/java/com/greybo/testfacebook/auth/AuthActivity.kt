package com.greybo.testfacebook.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.greybo.testfacebook.R
import com.greybo.testfacebook.album.AlbumActivity
import java.util.*


class AuthActivity : AppCompatActivity() {
    private val TAG: String = "AuthActivity"

    private lateinit var mCallbackManager: CallbackManager
    private lateinit var loginButton: LoginButton

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, AuthActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        mCallbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button)

        val accessToken = AccessToken.getCurrentAccessToken()

        if (accessToken != null && !accessToken.isExpired()) {
            nextActivity()
        } else {
            signIn()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun signIn() {
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_photos"))
        loginButton.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onError(error: FacebookException?) {
                Log.d(TAG, "facebook:onError", error);
                Toast.makeText(this@AuthActivity, error?.message, Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess(result: LoginResult?) {
                val accessToken = result?.accessToken
                if (accessToken != null && !accessToken.isExpired()) {
                    nextActivity()
                }
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }
        });
    }

    private fun nextActivity() {
        AlbumActivity.start(this@AuthActivity)
        finish()
    }

}
