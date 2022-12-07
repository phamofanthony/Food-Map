package com.example.foodmap.MapsActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.foodmap.R
import kotlinx.coroutines.launch

class MapsViewModel(): ViewModel() {
    /*
    @Suppress("RedudndantSuspendModifier")
    @WorkerThread

    suspend fun getCaption(id:Int):String {
        Log.d("Alpha", "Entered getCaption")
        var result = "Replace me with caption"
        viewModelScope.launch {
            result = repository.getGeoPhotoCaptionById(id)
        }
        Log.d("Alpha", "Left getCaption")
        return result
    } */

    //val allGeoPhoto: LiveData<Map<Int, GeoPhoto>> = repository.allGeophotos.asLiveData()

    /*
    class ToDoListViewModelFactory(private val repository: GeoPhotosRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MapsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    } */
}