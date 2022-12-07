package com.example.foodmap.Repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_reviews_table")
data class FoodReviewItem(
    @PrimaryKey var postID: Int? = -1,
    @ColumnInfo(name = "ownerID") var ownerID: String = "", //their ID is their email
    @ColumnInfo(name = "longitude") var longitude: Double = -1.0,
    @ColumnInfo(name = "latitude") var latitude: Double = -1.0,
    @ColumnInfo(name = "restName") var restName: String = "",
    @ColumnInfo(name = "restRating") var restRating: Double = -1.0,
    @ColumnInfo(name = "restPricing") var restPricing: Double = -1.0,
    @ColumnInfo(name = "restReview") var restReview: String = "",
    @ColumnInfo(name = "restPictureURL") var restPictureURL: String = "",
    @ColumnInfo(name = "User") var name: String = ""
    )
