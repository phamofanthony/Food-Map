package com.example.foodmap.FoodMapListActivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.activity.viewModels
import com.example.foodmap.AddEditFoodReviewActivity.AddEditFoodReviewActivity
import com.example.foodmap.R
import com.example.foodmap.FoodMapApplication
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FoodReviewItemListActivity : AppCompatActivity() {

    val startAddEditToDoActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if(result.resultCode== Activity.RESULT_OK){
            Log.d("MainActivity","Completed")
        }
    }

    private val toDoListViewModel: FoodReviewListViewModel by viewModels {
        FoodReviewListViewModel.ToDoListViewModelFactory((application as FoodMapApplication).repository)
    }


    fun recyclerAdapterItemClicked(itemId:Int){
        startAddEditToDoActivity.launch(Intent(this,AddEditFoodReviewActivity::class.java).putExtra(AddEditFoodReviewActivity.EXTRA_ID,itemId))
    }
    fun recyclerAdapterItemCheckboxClicked(itemId:Int,isChecked:Boolean){
        toDoListViewModel.updateChecked(itemId,isChecked)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = FoodReviewListAdapter(this::recyclerAdapterItemClicked,this::recyclerAdapterItemCheckboxClicked)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Add an observer on the LiveData returned by allToDoItems.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        toDoListViewModel.allToDoItems.observe(this) { todoitems ->
            // Update the cached copy of the words in the adapter.
            todoitems.let {
                adapter.submitList(it.values.toList())
            }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            startAddEditToDoActivity.launch(Intent(this,AddEditFoodReviewActivity::class.java))
        }
    }

}