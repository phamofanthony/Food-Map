package com.example.foodmap.Repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todoitems_table")
data class FoodReviewItem(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "title") var title:String,
    @ColumnInfo(name="content") var content:String,
    @ColumnInfo(name="due_date") var dueDate:Long?,
    @ColumnInfo(name="completed") var completed:Int
)