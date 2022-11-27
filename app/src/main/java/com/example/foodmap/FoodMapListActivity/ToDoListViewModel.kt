package com.example.foodmap.FoodMapListActivity

import androidx.lifecycle.*
import com.example.foodmap.Repository.FoodReviewItem
import com.example.foodmap.Repository.FoodReviewListRepository
import kotlinx.coroutines.launch


class ToDoListViewModel(private val repository: FoodReviewListRepository): ViewModel() {

    fun updateChecked(itemId: Int, checked: Boolean) {
        viewModelScope.launch {
            repository.updateCompleted(itemId, checked)
        }
    }

    val allToDoItems: LiveData<Map<Int,FoodReviewItem>> = repository.allToDoItems.asLiveData()

    class ToDoListViewModelFactory(private val repository: FoodReviewListRepository) : ViewModelProvider.Factory{
        override fun <T: ViewModel> create(modelClass: Class<T>): T{
            if(modelClass.isAssignableFrom(ToDoListViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return ToDoListViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }


}