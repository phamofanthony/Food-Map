package com.example.foodmap.AddEditFoodReviewActivity

import androidx.lifecycle.*
import com.example.foodmap.Repository.FoodReviewItem
import com.example.foodmap.Repository.FoodReviewListRepository
import kotlinx.coroutines.launch

class AddEditFoodReviewViewModel(private val repository: FoodReviewListRepository): ViewModel() {

    val _FoodReviewItem = MutableLiveData<FoodReviewItem>().apply { value=null }
    val FoodReviewItem:LiveData<FoodReviewItem>
        get() = _FoodReviewItem

    fun start(itemId:Int){
        viewModelScope.launch {
            repository.allReviewItems.collect{
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
            FoodReviewItem.value?.id?.let { repository.deleteReviewItem(it) }
        }
    }

    fun updateItem(FoodReviewItem: FoodReviewItem) {
        viewModelScope.launch {
            repository.updateReviewItem(FoodReviewItem)
        }
    }

    class AddEditToDoViewModelFactory(private val repository: FoodReviewListRepository) : ViewModelProvider.Factory{
        override fun <T: ViewModel> create(modelClass: Class<T>): T{
            if(modelClass.isAssignableFrom(AddEditFoodReviewViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return AddEditFoodReviewViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}