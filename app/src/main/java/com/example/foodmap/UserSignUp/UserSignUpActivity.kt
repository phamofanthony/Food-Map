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

class UserSignUpActivity : AppCompatActivity() {
    private lateinit var firstNameText: TextInputEditText
    private lateinit var lastNameText: TextInputEditText
    private lateinit var emailText: TextInputEditText
    private lateinit var passwordText: TextInputEditText
    private lateinit var confirmBtn: Button
    private lateinit var uuid: String
    var auth = Firebase.auth
    var db = FirebaseUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_sign_up)

        firstNameText = findViewById(R.id.signUpFirstName)
        lastNameText = findViewById(R.id.signUpLastName)
        emailText = findViewById(R.id.signUpEmail)
        passwordText = findViewById(R.id.signUpPassword)
        confirmBtn = findViewById(R.id.signUpConfirmBtn)

        confirmBtn.setOnClickListener {
            if (emailText.text.toString() == "" || passwordText.text.toString() == "") {
                Toast.makeText(baseContext, "Please ensure all fields are filled out.", Toast.LENGTH_LONG).show()
            }
            else {
                auth.createUserWithEmailAndPassword(emailText.text.toString(), passwordText.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {

                            // Sign in success, update UI with the signed-in user's information
                            Log.d("UserSignUpActivity", "createUserWithEmail:success")
                            val user = auth.currentUser

                            if (user != null) {
                                val uuid = user.uid

                                db.makeNewUserDocument(firstNameText.text.toString(), lastNameText.text.toString(), emailText.text.toString())

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
    }

    companion object {
        val USER_UUID = "com.example.assignment2solution.addedittodoactivity.USER_UUID"
    }
}