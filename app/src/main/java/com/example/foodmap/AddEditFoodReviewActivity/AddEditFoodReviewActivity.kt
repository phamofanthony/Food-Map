package com.example.foodmap.AddEditFoodReviewActivity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import androidx.activity.viewModels
import com.example.foodmap.Repository.FoodReviewItem
import com.example.foodmap.FoodMapApplication
import com.example.foodmap.Util.DatePickerFragment
import com.example.foodmap.Util.TimePickerFragment
import com.example.foodmap.R


import java.util.*

class AddEditFoodReviewActivity : AppCompatActivity() {

    private lateinit var reviewItem: FoodReviewItem
    private lateinit var etTitle: EditText
    private lateinit var etContent: EditText
    private lateinit var etPrice: EditText
//    private lateinit var ratingBar: RatingBar
    private lateinit var etReview: EditText
    lateinit var imageView: ImageView

//    private lateinit var etDate: Button
//    private lateinit var checkBox: CheckBox

    private val addEditToDoViewModel: AddEditFoodReviewViewModel by viewModels {
        AddEditFoodReviewViewModel.AddEditToDoViewModelFactory((application as FoodMapApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_to_do)
        etTitle = findViewById(R.id.etToDoTitle)
        etContent = findViewById(R.id.etEditContent)
        etPrice = findViewById(R.id.etPrice)
//        ratingBar= findViewById(R.id.ratingBar)
        etReview = findViewById(R.id.etReviewContent)
        imageView = findViewById(R.id.imageView)

        val myUri: Uri? = Uri.parse(fileName)
        imageView.setImageURI(myUri)
//        etDate = findViewById(R.id.editTextDate)
//        checkBox = findViewById(R.id.cbMarkComplete)
        val id = intent.getIntExtra(EXTRA_ID, -1)
        if (id == -1) {
            populateNewreviewItem()
        } else {
            populateExistingreviewItem(id)
        }
    }

    fun populateNewreviewItem() {
        reviewItem = FoodReviewItem(null, "", 0.0, 0.0, "", "", 0, 0, 0.0,0.0f, "")
        updateViewUI()
    }

    fun populateExistingreviewItem(id: Int) {
        addEditToDoViewModel.start(id)
        addEditToDoViewModel.FoodReviewItem.observe(this) {
            if (it != null) {
                reviewItem = it
                updateViewUI()
            }
        }
    }

    fun updateViewUI() {

        etTitle.setText(reviewItem.title)
        etContent.setText(reviewItem.content)
        etPrice.setText(reviewItem.price.toString())
//        ratingBar.setRating(reviewItem.rating)
        etReview.setText(reviewItem.review)
//        if (reviewItem.dueDate != null) {
//            val cal: Calendar = Calendar.getInstance()
//            cal.timeInMillis = reviewItem.dueDate!!
//            etDate.setText(
//                java.text.DateFormat.getDateTimeInstance(DEFAULT, SHORT).format(cal.timeInMillis)
//            )
//        } else {
//            etDate.setText("")
//        }
//        checkBox.isChecked = reviewItem.completed != 0
    }

    fun deleteClicked(view: View) {
        Log.d("AddEditDoDoActivity", "Delete Clicked")
        if (reviewItem.id == 0) {
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
        if (reviewItem.id == null) {
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
        reviewItem.title = etTitle.text.toString()
        reviewItem.content = etContent.text.toString()
        reviewItem.price = etPrice.text.toString().toDouble()
//        reviewItem.rating = ratingBar.rating.toFloat()//sera que es toRating()????
        reviewItem.review = etReview.text.toString()
//        reviewItem.dueDate = java.text.DateFormat.getDateTimeInstance(DEFAULT, SHORT)
//            .parse(etDate.text.toString())?.time
//        if (checkBox.isChecked) {
//            reviewItem.completed = 1
//        } else {
//            reviewItem.completed = 0
//        }
    }

//    fun dateSet(calendar: Calendar) {
//        TimePickerFragment(calendar, this::timeSet).show(supportFragmentManager, "timePicker")
//    }
//
//    fun timeSet(calendar: Calendar) {
//        etDate.setText(
//            java.text.DateFormat.getDateTimeInstance(DEFAULT, SHORT).format(calendar.timeInMillis)
//        )
//    }
//
//    fun dateClicked(view: View) {
//        DatePickerFragment(this::dateSet).show(supportFragmentManager, "datePicker")
//    }

    companion object {
        val EXTRA_ID = "com.example.foodmap.addedittodoactivity.id"
    }
}