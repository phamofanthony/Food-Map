package com.example.foodmap
import android.app.Application
import com.example.foodmap.Repository.FoodReviewListRepository
import com.example.foodmap.Repository.FoodReviewListRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class FoodMapApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { FoodReviewListRoomDatabase.getDatabase(this,applicationScope)}
    val repository by lazy{ FoodReviewListRepository(database.reviewListDAO())}
}