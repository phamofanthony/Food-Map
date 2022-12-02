package com.example.foodmap.Repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_reviews_table")
data class FoodReviewItem(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "ownerID") var ownerID: String,
    @ColumnInfo(name = "longitude") var longitude: Double,
    @ColumnInfo(name = "latitude") var latitude: Double,
    @ColumnInfo(name = "restName") var restName: String,
    @ColumnInfo(name = "restRating") var restRating: Int,
    @ColumnInfo(name = "restPricing") var restPricing: Int,
    @ColumnInfo(name = "restItemsOrdered") var restItemsOrdered: String,
    @ColumnInfo(name = "restReview") var restReview: String,
    @ColumnInfo(name = "restPictureURL") var restPictureURL: String
)
