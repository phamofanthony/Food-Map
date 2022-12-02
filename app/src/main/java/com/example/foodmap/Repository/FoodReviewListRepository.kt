package com.example.foodmap.Repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class FoodReviewListRepository(private val reviewListDao: FoodReviewListDao) {

    /*

    val allReviewItems: Flow<Map<Int,FoodReviewItem>> = reviewListDao.getReviewItems()

    @Suppress("RedudndantSuspendModifier")
    @WorkerThread
    suspend fun insert(foodReviewItem: FoodReviewItem){
        reviewListDao.insert(foodReviewItem)
    }

    @Suppress("RedudndantSuspendModifier")
    @WorkerThread
    suspend fun getReviewItem(reviewId: Int): FoodReviewItem {
        return reviewListDao.getItem(reviewId)
    }

    @Suppress("RedudndantSuspendModifier")
    @WorkerThread
    suspend fun updateCompleted(toDoId: Int, completed:Boolean) {
        if (completed)
            reviewListDao.updateCompleted(toDoId,1)
        else
            reviewListDao.updateCompleted(toDoId,0)
    }

    @Suppress("RedudndantSuspendModifier")
    @WorkerThread
    suspend fun deleteReviewItem(id: Int) {
        reviewListDao.deleteItem(id)
    }

    @Suppress("RedudndantSuspendModifier")
    @WorkerThread
    suspend fun updateReviewItem(reviewItem: FoodReviewItem) {
        reviewListDao.updateItem(reviewItem)
    }
    */
}