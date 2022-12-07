package com.example.foodmap.MapsActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.example.foodmap.R
import com.example.foodmap.Repository.FoodReviewItem
import com.example.foodmap.Repository.FoodReviewListRepository
import kotlinx.coroutines.launch

class MapsViewModel(private val repository: FoodReviewListRepository): ViewModel() {

    @Suppress("RedudndantSuspendModifier")
    @WorkerThread

    suspend fun getCaption(id:Int):String {
        Log.d("Alpha", "Entered getCaption")
        var result = "Replace me with caption"
        viewModelScope.launch {
            //result = repository.getGeoPhotoCaptionById(id)
        }
        Log.d("Alpha", "Left getCaption")
        return result
    }

    val allReviews: LiveData<Map<Int, FoodReviewItem>> = repository.allReviewItems.asLiveData()


    class FoodReviewListListViewModelFactory(private val repository: FoodReviewListRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MapsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}