package com.example.foodmap.AddEditFoodReviewActivity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.FileProvider
import com.example.foodmap.Repository.FoodReviewItem
import com.example.foodmap.FoodMapApplication
import com.example.foodmap.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class AddEditFoodReviewActivity : AppCompatActivity() {

    private lateinit var reviewItem: FoodReviewItem
    private lateinit var etTitle: EditText
    private lateinit var etReview: EditText
    private lateinit var etPrice: EditText

    private lateinit var ratingBar: RatingBar
    lateinit var imageView: ImageView

    var currentPhotoPath: String = ""

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
        imageView = findViewById(R.id.imageView)
        val id = intent.getIntExtra(EXTRA_ID, -1)
        if (id == -1) {
            populateNewFoodReview()
        } else {
            populateExistingFoodReview(id)
        }

        findViewById<FloatingActionButton>(R.id.photoActionBtn).setOnClickListener {
            takeNewPhoto()
        }
    }

    fun populateNewFoodReview() {
        reviewItem = FoodReviewItem(
            null, "", 0.0, 0.0, "",
            1.0, 0.0, "Review...", "", "Test User"
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
        if (reviewItem.restPictureURL != ""){
            val myUri: Uri? = Uri.parse(reviewItem.restPictureURL)
            imageView.setImageURI(myUri)
        }
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


    // MAP SECTION
    private val takePictureResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            /**
             * Activity result launcher that listens for Camera Service Result and also launches
             * The Camera Service
             */
            if (result.resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(applicationContext, "No picture taken", Toast.LENGTH_LONG)
                currentPhotoPath=""
            } else {
                Log.d("MainActivity", "Picture Taken at location $currentPhotoPath")
                //        imageView = findViewById(R.id.imageView)
                    val myUri: Uri? = Uri.parse(currentPhotoPath)
                    imageView.setImageURI(myUri)
            }
        }


    // PHOTO SECTION
    private fun takeNewPhoto() {
        /**
         * Creates a photo URI path and adds that to the media store photo extra
         * telling it where to save the image
         */
        Log.d("MainActivity", "TAKE A PHOTO BUTTON CLICKED")
//        getLastLocation(this, locationProviderClient, locationUtilCallback)
        val picIntent = Intent().setAction(MediaStore.ACTION_IMAGE_CAPTURE)
        if (picIntent.resolveActivity(packageManager) != null) {
            val filepath: String = createFilePath()
            val myFile: File = File(filepath)
            currentPhotoPath = filepath
            val photoUri = FileProvider.getUriForFile(
                this,
                "com.example.foodmap.fileprovider",
                myFile
            )
            picIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            takePictureResultLauncher.launch(picIntent)
        }
    }

    private fun createFilePath(): String {
        // Create an image file name
        val timeStamp =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )
        // Save a file: path for use with ACTION_VIEW intent
        return image.absolutePath
    }


    private fun getFieldsIntoItem() {
        reviewItem.restName = etTitle.text.toString()
        reviewItem.restReview = etReview.text.toString()
        reviewItem.restPricing = etPrice.text.toString().toDouble()
        reviewItem.restRating = ratingBar.rating.toDouble()
        reviewItem.restPictureURL = currentPhotoPath
    }

    companion object {
        val EXTRA_ID = "com.example.foodmap.addedittodoactivity.id"
    }

}