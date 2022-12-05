package com.example.foodmap.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.foodmap.AddEditFoodReviewActivity.AddEditFoodReviewViewModel
import com.example.foodmap.FoodMapListActivity.FoodReviewListViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

class FirebaseUtil {
    val connection = FirebaseFirestore.getInstance()

    /*Add follow to follower's following list
    * @param[followerUserID] Person wanting to follow someone new
    * @param[followeeUserID] Person that follower wants to follow
    * @TODO: Implement
    */
    fun addFollow(followerUserEmail: String, followeeUserEmail: String) {
        //Check if followeeUserEmail exists in DB
        TODO()
    }


    /* Checks if a userID exists in the database
    * @param[id] ID of user we want to see if exists in the database (checking community collection)
    * @TODO: Implement
    */
    fun doesUserExist(email: String): Boolean {
        TODO()
        //val docRef = connection.collection()
    }


    /* Add a new food review document
    * @param[foodReview] FoodReview item passed in to be added to the DB
    * @param[foodReviewVM] ViewModel passed in to access DAO
    */
    fun addFoodReview(foodReview: FoodReviewItem) {
        getNewCount() {result ->
            var reviewsRef = connection.collection("FoodReviews") //Reference to the reviews collection
            var newReview = hashMapOf(
                "id" to result,
                "latitude" to foodReview.latitude,
                "longitude" to foodReview.longitude,
                "ownerID" to getCurrentUserEmail(),
                "restName" to foodReview.restName,
                "restPictureURL" to foodReview.restPictureURL,
                "restPricing" to foodReview.restPricing,
                "restRating" to foodReview.restRating,
                "restReview" to foodReview.restReview
            )
            reviewsRef.document("CURRENTUSEREMAIL" + "_" + foodReview.restName).set(newReview)
        }
    }


    /*
    * Used within the addFoodReview function to generate a new count for the postID
    */
    fun getNewCount(callback:(Int) -> Unit) {
        //Get current iterator value
        var countRef = connection.collection("FoodReviews").document("Iterator")
        var currentValue: Int = -1
        var updatedValue: Int = -1
        countRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    currentValue = document.data?.get("Count").toString().toInt()
                    updatedValue = currentValue + 1
                    Log.d("FirebaseUtil", "getNewCount: Successfully captured count value. Current value is now " + updatedValue)
                    val newCount = hashMapOf(
                        "Count" to updatedValue
                    )
                    countRef.set(newCount)
                    callback(updatedValue)

                } else {
                    Log.d("FirebaseUtil", "Iterator document DNE")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("FirebaseUtil", "Failed to get reference, exception: " + exception)
            }
    }


    /* Modify an existing food review document
    * May replace id with another way to identify food reviews amongst all users
    * @param[id] Integer identifier for the review being modified
    * @param[foodReviewItem] Food review item to be replace the one being modified
    */
    fun modifyFoodReview(foodReviewItem: FoodReviewItem) {
        connection.collection("FoodReviews").document(foodReviewItem.ownerID + "_" + foodReviewItem.restName)
            .set(foodReviewItem)
    }


    /* Query all food reviews from users that current user is following
    * @param[currentUserID] The current user who we want to find all the visible reviews for (checking their following list in community collection)
    * @TODO: Implement
    */
    fun queryVisibleReviews(currentUserEmail: String): MutableList<FoodReviewItem> {
        var visibleReviews: MutableList<FoodReviewItem> = mutableListOf<FoodReviewItem>()
        connection.collection("CommunityInfo")
            .whereEqualTo("Email", currentUserEmail)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val foodReviewInstance: FoodReviewItem = document.toObject<FoodReviewItem>()
                    visibleReviews.add(foodReviewInstance)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(
                    "FirebaseUtil",
                    "queryVisibleReviews failed"
                )
            }
        return visibleReviews
    }


    /* Is called whenever a new user is created (sets up their community profile)
    * @param[fname] First name
    * @param[lname] Last name
    * @param[email] Email
    */
    fun makeNewUserDocument(fname: String, lname: String, email: String) {
        var communityRef = connection.collection("CommunityInfo") //Reference to the CommunityInfo collection
        val newUserInfo = hashMapOf(
            "FirstName" to fname,
            "LastName" to lname,
            "Email" to email,
            "FollowingList" to email
        )
        communityRef.document(email).set(newUserInfo) //Add an item to the collection
    }


    /* Can be called to figure out email of current user session
    * @param[fname] Result of calling Firebase.auth.currentUser.uid
    */
    fun getCurrentUserEmail(): String {
        return Firebase.auth.currentUser?.email.toString()

    }


    /* Used to delete a user from DB
    * @param[foodReview] Review to be deleted
    */
    fun deleteFoodReview(foodReview: FoodReviewItem) {
        connection.collection("FoodReviews").document(foodReview.ownerID + "_" + foodReview.restName)
            .delete()
    }
}