package com.example.foodmap.FoodMapListActivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.activity.viewModels
import com.example.foodmap.AddEditFoodReviewActivity.AddEditFoodReviewActivity
import com.example.foodmap.AddFollowActivity.AddFollowActivity
import com.example.foodmap.R
import com.example.foodmap.FoodMapApplication
import com.example.foodmap.MapsActivity.MapsActivity
import com.example.foodmap.Repository.FirebaseUtil
import com.example.foodmap.Repository.FoodReviewItem
import com.example.foodmap.UserSignUp.UserEntryActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.toObject

class FoodReviewItemListActivity : AppCompatActivity() {

    private var user1Uuid = "0"

    //Launches Add/Edit activity
    val startAddEditToDoActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                Log.d("MainActivity", "Completed")
            }
        }

    //Launches new friend/following activity
    private val startFriendsActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                Log.d("MainActivity", "Completed")
            }
        }

    //Launches login/signup activity
    val startUserEntryActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intentData = result.data
                intentData?.getStringExtra(UserEntryActivity.USER_UUID)?.let { userData ->
                    user1Uuid = userData.toString()
                    Log.d("MainActivity", "SIGNING IN USER $user1Uuid")
                    Log.d(
                        "MainActivity",
                        "Email of current user is " + FirebaseUtil().getCurrentUserEmail()
                    )
                    foodReviewListViewModel.purgeDB()

                    FirebaseUtil().connection.collection("CommunityInfo")
                        .whereEqualTo("Email", FirebaseUtil().getCurrentUserEmail())
                        .get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                followingString = (document.data?.get("FollowingList")
                                    .toString())
                            }
                            followingList = followingString.split(",").map {it.trim()}
                            Log.d("MainActivity", "Following List: " + followingList.toString())
                            subscribeToRealtimeUpdates()
                        }
                }
            }
        }

    private val foodReviewListViewModel: FoodReviewListViewModel by viewModels {
        FoodReviewListViewModel.FoodReviewListViewModelFactory((application as FoodMapApplication).repository)
    }

    private val startMapsActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                Log.d("MainActivity", "Completed")
            }
        }

    fun recyclerAdapterItemClicked(itemId: Int) {
        startAddEditToDoActivity.launch(
            Intent(
                this,
                AddEditFoodReviewActivity::class.java
            ).putExtra(AddEditFoodReviewActivity.EXTRA_ID, itemId)
        )
    }

    lateinit var followingString: String
    var followingList: List<String> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = FoodReviewListAdapter(this::recyclerAdapterItemClicked)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        foodReviewListViewModel.allReviewItems.observe(this) { reviewitem ->
            // Update the cached copy of the words in the adapter.
            reviewitem.let {
                adapter.submitList(it.values.toList())
            }
        }

        var addToDoActionBtn = findViewById<ImageButton>(R.id.AddBtn)
        addToDoActionBtn.setOnClickListener {
            startAddEditToDoActivity.launch(Intent(this, AddEditFoodReviewActivity::class.java))
        }

        val mapsActionBtn = findViewById<ImageButton>(R.id.mapBtn)
        mapsActionBtn.setOnClickListener {
            startMapsActivity.launch(Intent(this, MapsActivity::class.java))
        }

        val friendsActionBtn = findViewById<ImageButton>(R.id.friendsBtn)
        friendsActionBtn.setOnClickListener {
            startFriendsActivity.launch(Intent(this,AddFollowActivity::class.java))
        }

        if (user1Uuid == "0"){
            val launchIntent = Intent(this@FoodReviewItemListActivity, UserEntryActivity::class.java)
            startUserEntryActivity.launch(launchIntent)
        }

    }


    private fun subscribeToRealtimeUpdates() {
        val db = FirebaseUtil()
        db.connection.collection("FoodReviews")
            .whereIn(
                "ownerID",
                followingList
            ) //Eventually, have call to get currUserEmail + implement following system
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