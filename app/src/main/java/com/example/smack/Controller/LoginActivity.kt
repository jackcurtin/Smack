package com.example.smack.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.smack.R
import com.example.smack.Services.AuthService

class LoginActivity : AppCompatActivity() {
    lateinit var loginEmailTxt: TextView
    lateinit var loginPasswordTxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginEmailTxt = findViewById(R.id.loginEmailTxt)
        loginPasswordTxt = findViewById(R.id.loginPasswordTxt)
        setContentView(R.layout.activity_login)
    }

    fun loginCreateUserBtnClicked(view: View){
        val createUserIntent = Intent(this, CreateUserActivity::class.java)
        startActivity(createUserIntent)
        finish()

    }

    fun loginLoginBtnClicked(view: View){
        val email = loginEmailTxt.text.toString()
        val password = loginPasswordTxt.text.toString()

        AuthService.loginUser(this, email, password) { loginSuccess ->
            if(loginSuccess) {
                AuthService.findUserByEmail(this) { findSuccess ->
                    if(findSuccess){
                        finish()
                    }
                }
            }
        }
    }
}