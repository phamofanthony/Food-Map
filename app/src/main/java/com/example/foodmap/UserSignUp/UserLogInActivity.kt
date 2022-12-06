package com.example.foodmap.UserSignUp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.foodmap.R
import com.example.foodmap.Repository.FirebaseUtil
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserLogInActivity : AppCompatActivity() {

    private lateinit var emailText: TextInputEditText
    private lateinit var passwordText: TextInputEditText
    private lateinit var confirmBtn: Button
    private lateinit var uuid: String
    var auth = Firebase.auth
    var db = FirebaseUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_log_in)

        emailText = findViewById(R.id.logInEmail)
        passwordText = findViewById(R.id.logInPassword)
        confirmBtn = findViewById(R.id.logInConfirmBtn)

        confirmBtn.setOnClickListener {
            if (emailText.text.toString() == "" || passwordText.text.toString() == "") {
                Toast.makeText(baseContext, "Please ensure both email and password are filled out.", Toast.LENGTH_LONG).show()
            }
            else {
                auth.signInWithEmailAndPassword(emailText.text.toString(), passwordText.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("UserSignUpActivity", "signInWithEmail:success")
                            val user = auth.currentUser
                            if (user != null) {
                                val uuid = user.uid
                                val replyIntent = Intent()
                                replyIntent.putExtra(USER_UUID, uuid)
                                setResult(Activity.RESULT_OK, replyIntent)
                                finish()
                            }
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.w("UserSignUpActivity", "signInWithEmail:failure", task.exception)
                            val message = task.exception?.message
                            Toast.makeText(
                                baseContext, "$message",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

    }

    companion object {
        val USER_UUID = "com.example.assignment2solution.addedittodoactivity.USER_UUID"
    }
}