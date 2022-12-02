package com.example.foodmap.Repository

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow

class FirebaseUtil {
    val connection = Firebase.firestore

    /*Add follow to follower's following list
    * @param[followerUserID] Person wanting to follow someone new
    * @param[followeeUserID] Person that follower wants to follow
    * @TODO: Implement
    */
    fun addFollow(followerUserID: String, followeeUserID: String) {
        //Check if followeeUserID exists in DB ß
    }

    /* Add a new food review document
    * @param[foodReview] FoodReview item passed in to be added to the DB
    */
    fun addFoodReview(foodReview : FoodReviewItem) {
        var reviewsRef = connection.collection("FoodReviews") //Reference to the reviews collection
        reviewsRef.add(foodReview) //Add an item to the collection
    }

    /* Modify an existing food review document
    * May replace id with another way to identify food reviews amongst all users
    * @param[id] Integer identifier for the review being modified
    * @param[foodReviewItem] Food review item to be replace the one being modified
    * @TODO: Implement
    */
    fun modifyFoodReview(id: Int, foodReviewItem : FoodReviewItem) {

    }

    /* Query all food reviews from users that current user is following
    * @param[currentUserID] The current user who we want to find all the visible reviews for (checking their following list in community collection)
    * @TODO: Implement
    */
    fun queryVisibleReviews(currentUserID: String): Flow<Map<Int, FoodReviewItem>>{
        TODO()
    }

    /* Checks if a userID exists in the database
    * @param[id] ID of user we want to see if exists in the database (checking community collection)
    * @TODO: Implement
    */
    fun doesUserExist(id: Int) {

    }
}