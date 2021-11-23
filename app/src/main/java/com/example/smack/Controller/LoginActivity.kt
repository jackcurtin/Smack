package com.example.smack.Controller

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.smack.R
import com.example.smack.Services.AuthService

class LoginActivity : AppCompatActivity() {
    lateinit var loginEmailTxt: TextView
    lateinit var loginPasswordTxt: TextView
    lateinit var loginSpinner: ProgressBar
    lateinit var loginCreateUserBtn : Button
    lateinit var loginLoginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginSpinner = findViewById(R.id.loginSpinner)
        loginCreateUserBtn = findViewById(R.id.loginCreateUserBtn)
        loginLoginBtn = findViewById(R.id.loginLoginBtn)
        loginEmailTxt = findViewById(R.id.loginEmailTxt)
        loginPasswordTxt = findViewById(R.id.loginPasswordTxt)

        loginSpinner.visibility = View.INVISIBLE
    }

    fun loginCreateUserBtnClicked(view: View){
        val createUserIntent = Intent(this, CreateUserActivity::class.java)
        startActivity(createUserIntent)
        finish()

    }

    fun loginLoginBtnClicked(view: View){
        enableSpinner(true)
        val email = loginEmailTxt.text.toString()
        val password = loginPasswordTxt.text.toString()
        hideKeyboard()
        if (email.isNotEmpty() && password.isNotEmpty()){
            AuthService.loginUser(this, email, password) { loginSuccess ->
                if(loginSuccess) {
                    AuthService.findUserByEmail(this) { findSuccess ->
                        if(findSuccess){
                            enableSpinner(false)
                            finish()
                        } else {
                            errorToast()
                        }
                    }
                } else {
                    errorToast()
                }
            }
        } else {
            Toast.makeText(this, "Please fill in both email and password", Toast.LENGTH_LONG).show()
        }
    }

    fun errorToast() {
        Toast.makeText(this, "Something went wrong, please try again.", Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }

    fun enableSpinner(enable: Boolean){
        if (enable) {
            loginSpinner.visibility = View.VISIBLE
        } else {
            loginSpinner.visibility = View.INVISIBLE
        }
        loginCreateUserBtn.isEnabled = !enable
        loginLoginBtn.isEnabled = !enable
    }

    fun hideKeyboard() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if (inputManager.isAcceptingText) {
            inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }
}