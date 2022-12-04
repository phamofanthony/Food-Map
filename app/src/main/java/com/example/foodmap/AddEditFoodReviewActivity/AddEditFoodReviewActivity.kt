package com.example.foodmap.AddEditFoodReviewActivity

import android.icu.text.DateFormat.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.activity.viewModels
import com.example.foodmap.Repository.FoodReviewItem
import com.example.foodmap.FoodMapApplication
import com.example.foodmap.Util.DatePickerFragment
import com.example.foodmap.Util.TimePickerFragment
import com.example.foodmap.R
import com.example.foodmap.Repository.FirebaseUtil


import java.util.*

class AddEditFoodReviewActivity : AppCompatActivity() {

    private lateinit var reviewItem: FoodReviewItem
    private lateinit var etTitle: EditText
    private lateinit var etContent: EditText
    private lateinit var etDate: Button
    private lateinit var checkBox: CheckBox


    private val addEditToDoViewModel: AddEditFoodReviewViewModel by viewModels {
        AddEditFoodReviewViewModel.AddEditToDoViewModelFactory((application as FoodMapApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_to_do)
        var testItem = FoodReviewItem(-1, "anotherEmail@gmail.com", 10.0, 10.0, "Cane's", 1, 5, "Fish", "Mid", "google.com")
        addEditToDoViewModel.deleteFoodReviewItem()
        /*
        etTitle = findViewById(R.id.etToDoTitle)
        etContent = findViewById(R.id.etEditContent)
        etDate = findViewById(R.id.editTextDate)
        checkBox = findViewById(R.id.cbMarkComplete)
        val id = intent.getIntExtra(EXTRA_ID, -1)
        if (id == -1) {
            populateNewreviewItem()
        } else {
            populateExistingreviewItem(id)
        } */
    }
    /*
    fun populateNewreviewItem() {
        reviewItem = FoodReviewItem(null, "", 0.0, 0.0, "", "", 0, 0)
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
    } */
    /*
    fun updateViewUI() {
        etTitle.setText(reviewItem.title)
        etContent.setText(reviewItem.content)
        if (reviewItem.dueDate != null) {
            val cal: Calendar = Calendar.getInstance()
            cal.timeInMillis = reviewItem.dueDate!!
            etDate.setText(
                java.text.DateFormat.getDateTimeInstance(DEFAULT, SHORT).format(cal.timeInMillis)
            )
        } else {
            etDate.setText("")
        }
        checkBox.isChecked = reviewItem.completed != 0
    } */
    /*
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
    } */
    /*
    private fun getFieldsIntoItem() {
        reviewItem.title = etTitle.text.toString()
        reviewItem.content = etContent.text.toString()
        reviewItem.dueDate = java.text.DateFormat.getDateTimeInstance(DEFAULT, SHORT)
            .parse(etDate.text.toString())?.time
        if (checkBox.isChecked) {
            reviewItem.completed = 1
        } else {
            reviewItem.completed = 0
        }
    } */

    fun dateSet(calendar: Calendar) {
        TimePickerFragment(calendar, this::timeSet).show(supportFragmentManager, "timePicker")
    }

    fun timeSet(calendar: Calendar) {
        etDate.setText(
            java.text.DateFormat.getDateTimeInstance(DEFAULT, SHORT).format(calendar.timeInMillis)
        )
    }

    fun dateClicked(view: View) {
        DatePickerFragment(this::dateSet).show(supportFragmentManager, "datePicker")
    }

    companion object {
        val EXTRA_ID = "com.example.foodmap.addedittodoactivity.id"
    }

}