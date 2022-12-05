package com.example.foodmap.UserSignUp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.foodmap.R.*
import com.example.foodmap.R.id.*

class UserSignUpActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signUpBtn: Button
    private lateinit var signInBtn: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_user_signup)
        auth = Firebase.auth
        usernameEditText = findViewById(editTextTextEmailAddress)
        passwordEditText = findViewById(editTextTextPassword)
        signUpBtn = findViewById(signUpButton)
        signInBtn = findViewById(signInButton)

        signUpBtn.setOnClickListener {
            signUpUser()
        }

        signInBtn.setOnClickListener {
            signInUser()
        }
    }

    companion object {
        val USER_UUID = "com.example.assignment2solution.addedittodoactivity.USER_UUID"
    }

    private fun signUpUser() {
        if (usernameEditText.text.toString() == "" || passwordEditText.text.toString() == "") {
            Toast.makeText(
                baseContext,
                "Please ensure both username and password are filled out.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            auth.createUserWithEmailAndPassword(
                usernameEditText.text.toString(),
                passwordEditText.text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        // Sign in success, update UI with the signed-in user's information
                        Log.d("UserSignUpActivity", "createUserWithEmail:success")
                        val user = auth.currentUser

                        if (user != null) {
                            val uuid = user.uid
                            val replyIntent = Intent()
                            replyIntent.putExtra(USER_UUID, uuid)
                            setResult(Activity.RESULT_OK, replyIntent)
                            finish()
                        }

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("UserSignUpActivity", "createUserWithEmail:failure", task.exception)
                        val message = task.exception?.message
                        Toast.makeText(
                            baseContext, "$message",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    private fun signInUser() {
        if (usernameEditText.text.toString() == "" || passwordEditText.text.toString() == "") {
            Toast.makeText(
                baseContext,
                "Please ensure both username and password are filled out.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            auth.signInWithEmailAndPassword(
                usernameEditText.text.toString(),
                passwordEditText.text.toString()
            )
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
                    } else {
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