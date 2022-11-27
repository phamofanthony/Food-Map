package com.example.foodmap.AddEditToDoActivity

import androidx.lifecycle.*
import com.example.foodmap.Repository.FoodReviewItem
import com.example.foodmap.Repository.FoodReviewListRepository
import com.example.foodmap.FoodMapListActivity.ToDoListViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.withIndex
import kotlinx.coroutines.launch

class AddEditToDoViewModel(private val repository: FoodReviewListRepository): ViewModel() {

    val _FoodReviewItem = MutableLiveData<FoodReviewItem>().apply { value=null }
    val FoodReviewItem:LiveData<FoodReviewItem>
        get() = _FoodReviewItem

    fun start(itemId:Int){
        viewModelScope.launch {
            repository.allFoodReviewItems.collect{
                _FoodReviewItem.value = it[itemId]
            }
        }
    }

    fun insert(FoodReviewItem: FoodReviewItem) {
        viewModelScope.launch {
            repository.insert(FoodReviewItem)
        }
    }

    fun deleteFoodReviewItem() {
        viewModelScope.launch {
            FoodReviewItem.value?.id?.let { repository.deleteFoodReviewItem(it) }
        }
    }

    fun updateItem(FoodReviewItem: FoodReviewItem) {
        viewModelScope.launch {
            repository.updateItem(FoodReviewItem)
        }
    }

    class AddEditToDoViewModelFactory(private val repository: FoodReviewListRepository) : ViewModelProvider.Factory{
        override fun <T: ViewModel> create(modelClass: Class<T>): T{
            if(modelClass.isAssignableFrom(AddEditToDoViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return AddEditToDoViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}