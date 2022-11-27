package com.example.foodmap.FoodMapListActivity

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.activity.viewModels
import com.example.foodmap.FoodMapListActivity.ToDoListViewModel
import com.example.foodmap.AddEditToDoActivity.AddEditToDoActivity
import com.example.foodmap.R
import com.example.foodmap.Repository.FoodReviewItem
import com.example.foodmap.FoodMapApplication
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class ToDoListActivity : AppCompatActivity() {

    val startAddEditToDoActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if(result.resultCode== Activity.RESULT_OK){
            Log.d("MainActivity","Completed")
        }
    }

    private val toDoListViewModel: ToDoListViewModel by viewModels {
        ToDoListViewModel.ToDoListViewModelFactory((application as FoodMapApplication).repository)
    }


    fun recyclerAdapterItemClicked(itemId:Int){
        startAddEditToDoActivity.launch(Intent(this,AddEditToDoActivity::class.java).putExtra(AddEditToDoActivity.EXTRA_ID,itemId))
    }
    fun recyclerAdapterItemCheckboxClicked(itemId:Int,isChecked:Boolean){
        toDoListViewModel.updateChecked(itemId,isChecked)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ToDoListAdapter(this::recyclerAdapterItemClicked,this::recyclerAdapterItemCheckboxClicked)
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
            startAddEditToDoActivity.launch(Intent(this,AddEditToDoActivity::class.java))
        }
    }

}