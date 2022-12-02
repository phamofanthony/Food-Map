package com.example.foodmap.Repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseUtil {
    val connection = Firebase.firestore

    //Add follow to follower's following list
    //Follower: Person wanting to follow someone new
    //Followee: Person that follower wants to follow
    fun addFollow(followerUserID: String, followeeUserID: String) {

    }

    //Add a new food review document
    fun addFoodReview (foodReview : FoodReviewItem) {

    }

    //Modify an existing food review document
    //May replace id with another way to identify food reviews amongst all users
    fun modifyFoodReview(id: Int, foodReviewItem : FoodReviewItem) {

    }

    //Query all food reviews from users that current user is following
    fun queryVisibleReviews(currentUserID: String) {

    }
}