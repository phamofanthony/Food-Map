package com.example.foodmap.FoodMapListActivity

import androidx.lifecycle.*
import com.example.foodmap.Repository.FoodReviewItem
import com.example.foodmap.Repository.FoodReviewListRepository
import kotlinx.coroutines.launch


class FoodReviewListViewModel(private val repository: FoodReviewListRepository): ViewModel() {

    fun updateChecked(itemId: Int, checked: Boolean) {
        viewModelScope.launch {
            repository.updateCompleted(itemId, checked)
        }
    }

    val allToDoItems: LiveData<Map<Int,FoodReviewItem>> = repository.allToDoItems.asLiveData()

    class ToDoListViewModelFactory(private val repository: FoodReviewListRepository) : ViewModelProvider.Factory{
        override fun <T: ViewModel> create(modelClass: Class<T>): T{
            if(modelClass.isAssignableFrom(FoodReviewListViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return FoodReviewListViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }

}