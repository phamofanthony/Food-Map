package com.example.foodmap.FoodMapListActivity

import androidx.lifecycle.*
import com.example.foodmap.Repository.FoodReviewItem
import com.example.foodmap.Repository.FoodReviewListRepository
import kotlinx.coroutines.launch


class FoodReviewListViewModel(private val repository: FoodReviewListRepository): ViewModel() {

    val allReviewItems: LiveData<Map<Int,FoodReviewItem>> = repository.allReviewItems.asLiveData()

    fun purgeDB() {
        viewModelScope.launch {
            repository.purgeDB()
        }
    }
    fun insertItem(foodReview: FoodReviewItem) {
        viewModelScope.launch {
            repository.insert(foodReview)
        }
    }

    class FoodReviewListViewModelFactory(private val repository: FoodReviewListRepository) : ViewModelProvider.Factory{
        override fun <T: ViewModel> create(modelClass: Class<T>): T{
            if(modelClass.isAssignableFrom(FoodReviewListViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return FoodReviewListViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }

}