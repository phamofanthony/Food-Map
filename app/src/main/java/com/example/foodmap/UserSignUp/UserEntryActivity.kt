package com.example.foodmap.UserSignUp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.foodmap.R.*
import com.example.foodmap.R.id.*
import com.example.foodmap.Repository.FirebaseUtil

class UserEntryActivity : AppCompatActivity() {

    private lateinit var signUpBtn: Button
    private lateinit var logInBtn: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseUtil

    val startUserLogInActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                //Done
            }
        }

    val startUserSignUpActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                //Done
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_user_entry)
        auth = Firebase.auth
        db = FirebaseUtil()

        signUpBtn = findViewById(entrySignUpBtn)
        logInBtn = findViewById(entryLogInBtn)

        signUpBtn.setOnClickListener {
            val launchIntent = Intent(this@UserEntryActivity, UserSignUpActivity::class.java)
            startUserSignUpActivity.launch(launchIntent)
        }

        logInBtn.setOnClickListener {
            val launchIntent = Intent(this@UserEntryActivity, UserLogInActivity::class.java)
            startUserLogInActivity.launch(launchIntent)
        }
    }

    companion object {
        val USER_UUID = "com.example.assignment2solution.addedittodoactivity.USER_UUID"
    }
    /*
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
                        Log.d("UserEntryActivity", "createUserWithEmail:success")
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
                        Log.w("UserEntryActivity", "createUserWithEmail:failure", task.exception)
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
                        Log.d("UserEntryActivity", "signInWithEmail:success")
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
                        Log.w("UserEntryActivity", "signInWithEmail:failure", task.exception)
                        val message = task.exception?.message
                        Toast.makeText(
                            baseContext, "$message",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

        }
    } */
}