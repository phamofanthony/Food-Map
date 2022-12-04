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
import com.example.foodmap.Repository.FirebaseUtil
import com.example.foodmap.Repository.FoodReviewItem
import com.example.foodmap.UserSignUp.UserSignUpActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.toObject

class FoodReviewItemListActivity : AppCompatActivity() {

    private var user1Uuid = "0"

    val startAddEditToDoActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if(result.resultCode== Activity.RESULT_OK){
            Log.d("MainActivity","Completed")
        }
    }

    val startUserSignUpActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intentData = result.data
                intentData?.getStringExtra(UserSignUpActivity.USER_UUID)?.let { userData ->
                    user1Uuid = userData.toString()
                    Log.d("MainActivity", "SIGNING IN USER $user1Uuid")
                    Log.d("MainActivity", "Email of current user is " + FirebaseUtil().getCurrentUserEmail())
                    foodReviewListViewModel.purgeDB()
                    subscribeToRealtimeUpdates()
                }
            }
        }

    private val foodReviewListViewModel: FoodReviewListViewModel by viewModels {
        FoodReviewListViewModel.FoodReviewListViewModelFactory((application as FoodMapApplication).repository)
    }


    fun recyclerAdapterItemClicked(itemId:Int){
        startAddEditToDoActivity.launch(Intent(this,AddEditFoodReviewActivity::class.java).putExtra(AddEditFoodReviewActivity.EXTRA_ID,itemId))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        //val adapter = FoodReviewListAdapter(this::recyclerAdapterItemClicked,this::recyclerAdapterItemCheckboxClicked)
        //recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Add an observer on the LiveData returned by allToDoItems.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        foodReviewListViewModel.allReviewItems.observe(this) { reviewitem ->
            // Update the cached copy of the words in the adapter.
            reviewitem.let {
                //adapter.submitList(it.values.toList())
            }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            startAddEditToDoActivity.launch(Intent(this,AddEditFoodReviewActivity::class.java))
        }

        if (user1Uuid == "0"){
            val launchIntent = Intent(this@FoodReviewItemListActivity, UserSignUpActivity::class.java)
            startUserSignUpActivity.launch(launchIntent)
        }

    }

    private fun subscribeToRealtimeUpdates() {
        var db = FirebaseUtil()
        db.connection.collection("Food Reviews")
            .whereEqualTo("ownerID", "CURRENT_USER_EMAIL") //Eventually, have call to get currUserEmail + implement following system
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("MainActivity", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    Log.d("MainActivity", "Current data: ${snapshot.documents}")
                    for (doc in snapshot.documents) {
                        val dataToInsert = doc.toObject<FoodReviewItem>()
                        if (dataToInsert != null) {
                            foodReviewListViewModel.insertItem(dataToInsert)
                        }
                    }
                } else {
                    Log.d("MainActivity", "Current data: null")
                }
            }
    }
}