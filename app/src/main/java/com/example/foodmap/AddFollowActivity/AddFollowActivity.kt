package com.example.foodmap.AddFollowActivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.foodmap.R
import com.example.foodmap.R.*
import com.example.foodmap.Repository.FirebaseUtil
import com.example.foodmap.UserSignUp.UserLogInActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AddFollowActivity : AppCompatActivity() {

    private lateinit var emailText: EditText
    private lateinit var confirmFollowBtn: Button
    var db = FirebaseUtil()
    var auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_add_follow)

        emailText = findViewById(id.followText)
        confirmFollowBtn = findViewById(id.confirmFollowBtn)

        confirmFollowBtn.setOnClickListener {
            addFollow()
        }
    }

    fun addFollow() {
        var followingString = ""
        var newFollowEmail = emailText.text.toString()
        db.connection.collection("CommunityInfo").document(db.getCurrentUserEmail()).get()
            .addOnSuccessListener {
                followingString = it.data?.get("FollowingList").toString()
                followingString = followingString + "," + newFollowEmail
                db.connection.collection("CommunityInfo").document(db.getCurrentUserEmail()).update("FollowingList", followingString)
                    .addOnSuccessListener {
                        val replyIntent = Intent()
                        setResult(Activity.RESULT_OK, replyIntent)
                        finish()
                    }
            }

    }
}