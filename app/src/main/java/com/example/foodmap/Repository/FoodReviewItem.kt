package com.example.foodmap.Repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_reviews_table")
data class FoodReviewItem(
    @PrimaryKey var postID: Int?,
    @ColumnInfo(name = "ownerID") var ownerID: String, //their ID is their email
    @ColumnInfo(name = "longitude") var longitude: Double,
    @ColumnInfo(name = "latitude") var latitude: Double,
    @ColumnInfo(name = "restName") var restName: String,
    @ColumnInfo(name = "restRating") var restRating: Double,
    @ColumnInfo(name = "restPricing") var restPricing: Double,
    @ColumnInfo(name = "restItemsOrdered") var restItemsOrdered: String,
    @ColumnInfo(name = "restReview") var restReview: String,
    @ColumnInfo(name = "restPictureURL") var restPictureURL: String
)
