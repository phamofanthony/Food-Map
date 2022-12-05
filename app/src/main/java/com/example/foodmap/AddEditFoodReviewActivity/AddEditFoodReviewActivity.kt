package com.example.foodmap.AddEditFoodReviewActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import androidx.activity.viewModels
import com.example.foodmap.Repository.FoodReviewItem
import com.example.foodmap.FoodMapApplication
import com.example.foodmap.R

class AddEditFoodReviewActivity : AppCompatActivity() {

    private lateinit var reviewItem: FoodReviewItem
    private lateinit var etTitle: EditText
    private lateinit var etReview: EditText
    private lateinit var etPrice: EditText

    private lateinit var ratingBar: RatingBar
    lateinit var imageView: ImageView


    private val addEditToDoViewModel: AddEditFoodReviewViewModel by viewModels {
        AddEditFoodReviewViewModel.AddEditToDoViewModelFactory(
            (application as FoodMapApplication)
                .repository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_to_do)

        etTitle = findViewById(R.id.etToDoTitle)
        etPrice = findViewById(R.id.etPrice)
        ratingBar = findViewById(R.id.ratingBar)
        etReview = findViewById(R.id.etReviewContent)

        // TODO: Add Image Capability
//        imageView = findViewById(R.id.imageView)

//        val myUri: Uri? = Uri.parse("")
//        imageView.setImageURI(myUri)
//        val id = intent.getIntExtra(EXTRA_ID, -1)
//        if (id == -1) {
//            populateNewreviewItem()
//        } else {
//            populateExistingreviewItem(id)
//        }

        val id = intent.getIntExtra(EXTRA_ID, -1)
        if (id == -1) {
            populateNewFoodReview()
        } else {
            populateExistingFoodReview(id)
        }
    }

    fun populateNewFoodReview() {
        reviewItem = FoodReviewItem(
            null, "", 0.0, 0.0, "",
            1.0, 0.0,  "Review...", "", "Test User"
        )
        updateViewUI()
    }

    fun populateExistingFoodReview(id: Int) {
        addEditToDoViewModel.start(id)
        addEditToDoViewModel.FoodReviewItem.observe(this) {
            if (it != null) {
                reviewItem = it
                updateViewUI()
            }
        }
    }

    fun updateViewUI() {
        etTitle.setText(reviewItem.restName)
        etPrice.setText(reviewItem.restPricing.toString())
        ratingBar.rating = reviewItem.restRating.toFloat()
        etReview.setText(reviewItem.restReview)
    }

    fun deleteClicked(view: View) {
        Log.d("AddEditDoDoActivity", "Delete Clicked")
        if (reviewItem.postID == null) {
            setResult(RESULT_CANCELED)
            finish()
        } else {
            addEditToDoViewModel.deleteFoodReviewItem()
            setResult(RESULT_OK)
            finish()
        }
    }

    fun saveClicked(view: View) {
        Log.d("AddEditToDoActivity", "Save Clicked")
        if (reviewItem.postID == null) {
            getFieldsIntoItem()
            addEditToDoViewModel.insert(reviewItem)
            setResult(RESULT_OK)
            finish()
        } else {
            getFieldsIntoItem()
            addEditToDoViewModel.updateItem(reviewItem)
            setResult(RESULT_OK)
            finish()
        }
    }

    private fun getFieldsIntoItem() {
        reviewItem.restName = etTitle.text.toString()
        reviewItem.restReview = etReview.text.toString()
        reviewItem.restPricing = etPrice.text.toString().toDouble()
        reviewItem.restRating = ratingBar.rating.toDouble()
    }

    companion object {
        val EXTRA_ID = "com.example.foodmap.addedittodoactivity.id"
    }

}