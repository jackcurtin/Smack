package com.example.smack.Controller

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.smack.R
import com.example.smack.Services.AuthService

class CreateUserActivity : AppCompatActivity() {

    var userAvatar = "profileDefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"

    lateinit var avatarImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        avatarImageView= findViewById(R.id.createAvatarImageView)
    }

    fun generateUserAvatar(view: View){
        val random = java.util.Random()
        val color = random.nextInt(2)
        val avatar = random.nextInt(28)

        if(color == 0) {
            userAvatar = "light$avatar"
        } else {
            userAvatar = "dark$avatar"
        }
        val resourceId = resources.getIdentifier(userAvatar, "drawable", packageName)
        avatarImageView.setImageResource(resourceId)
    }

    fun generateColorClicked(view: View){
        val random = java.util.Random()
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)

        avatarImageView.setBackgroundColor(Color.rgb(r,g,b))

        val savedR = r.toDouble() / 255
        val savedG = g.toDouble() / 255
        val savedB = b.toDouble() / 255

        avatarColor = "[$savedR, $savedG, $savedB, 1]"
    }

    fun createUserClicked(view: View){
        val userName = findViewById<TextView>(findViewById(R.id.createUsernameTxt))
        val email = findViewById<TextView>(findViewById(R.id.createEmailTxt))
        val password = findViewById<TextView>(findViewById(R.id.createPasswordTxt))

        AuthService.registerUser(this, email.text.toString(), password.text.toString()) { registerSuccess ->
            if(registerSuccess){
                AuthService.loginUser(this, email.text.toString(), password.text.toString()) { loginSuccess ->
                    if(loginSuccess) {
                        AuthService.createUser(this, userName.text.toString(), email.text.toString(), userAvatar, avatarColor){createSuccess ->
                            if(createSuccess){
                                finish()
                            }
                        }

                    }
                }
            }

        }
    }
}