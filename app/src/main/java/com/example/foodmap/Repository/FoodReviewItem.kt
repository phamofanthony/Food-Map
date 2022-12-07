package com.example.foodmap.Repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_reviews_table")
data class FoodReviewItem(
    @PrimaryKey var postID: Int? = -1,
    @ColumnInfo(name = "ownerID") var ownerID: String = "defaultEmail", //their ID is their email
    @ColumnInfo(name = "longitude") var longitude: Double = -1.0,
    @ColumnInfo(name = "latitude") var latitude: Double = -1.0,
    @ColumnInfo(name = "restName") var restName: String = "defaultRestName",
    @ColumnInfo(name = "restRating") var restRating: Double = -1.0,
    @ColumnInfo(name = "restPricing") var restPricing: Double = -1.0,
    @ColumnInfo(name = "restReview") var restReview: String = "defaultrestReview",
    @ColumnInfo(name = "restPictureURL") var restPictureURL: String = "defaultPath",
    @ColumnInfo(name = "User") var name: String = "defaultName"
    )
