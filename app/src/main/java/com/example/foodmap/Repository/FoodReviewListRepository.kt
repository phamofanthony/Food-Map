package com.example.foodmap.Repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class FoodReviewListRepository(private val toDoListDao: FoodReviewListDao) {


    val allToDoItems: Flow<Map<Int,FoodReviewItem>> = toDoListDao.getToDoItems()


    @Suppress("RedudndantSuspendModifier")
    @WorkerThread
    suspend fun insert(toDoItem: FoodReviewItem){
        toDoListDao.insert(toDoItem)
    }

    @Suppress("RedudndantSuspendModifier")
    @WorkerThread
    suspend fun getToDoItem(toDoId: Int): FoodReviewItem {
        return toDoListDao.getItem(toDoId)
    }

    @Suppress("RedudndantSuspendModifier")
    @WorkerThread
    suspend fun updateCompleted(toDoId: Int, completed:Boolean) {
        if (completed)
            toDoListDao.updateCompleted(toDoId,1)
        else
            toDoListDao.updateCompleted(toDoId,0)
    }

    @Suppress("RedudndantSuspendModifier")
    @WorkerThread
    suspend fun deleteToDoItem(id: Int) {
        toDoListDao.deleteItem(id)
    }

    @Suppress("RedudndantSuspendModifier")
    @WorkerThread
    suspend fun updateItem(toDoItem: FoodReviewItem) {
        toDoListDao.updateItem(toDoItem)
    }

}