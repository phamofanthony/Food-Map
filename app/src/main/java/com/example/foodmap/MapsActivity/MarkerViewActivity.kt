package com.example.foodmap.MapsActivity

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import androidx.activity.viewModels
import com.example.foodmap.FoodMapApplication
import com.example.foodmap.R
import com.example.foodmap.Repository.FoodReviewItem
import kotlinx.coroutines.launch

class MarkerViewActivity : AppCompatActivity() {

    private var markerID: Int = -1
    private lateinit var reviewItem: FoodReviewItem
    private lateinit var titleText: EditText
    private lateinit var priceBar: RatingBar
    private lateinit var ratingBar: RatingBar
    private lateinit var reviewText: EditText
    private lateinit var reviewImageView: ImageView
    private val mapsViewModel: MapsViewModel by viewModels {
        MapsViewModel.FoodReviewListListViewModelFactory((application as FoodMapApplication).repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marker_view)
        Log.d("MarkerView", "In MarkerViewActivity")

        val markerIDString = intent.getStringExtra("ID").toString()
        markerID = markerIDString.toInt()

        titleText = findViewById(R.id.MarkerTitle)
        priceBar = findViewById(R.id.MarkerPriceBar)
        ratingBar = findViewById(R.id.MarkerRatingBar)
        reviewText = findViewById(R.id.MarkerReview)
        reviewImageView = findViewById(R.id.MarkerImageView)

        setView()
    }


    fun setView() {

        (applicationContext as FoodMapApplication).applicationScope.launch {
            reviewItem =
                (applicationContext as FoodMapApplication).repository.getReviewItem(
                    markerID
                )
            titleText.setText(reviewItem.restName)
            priceBar.setRating((reviewItem.restPricing).toFloat())
            ratingBar.setRating((reviewItem.restRating).toFloat())
            reviewText.setText(reviewItem.restReview)
            if (reviewItem.restPictureURL != ""){
                val myUri: Uri? = Uri.parse(reviewItem.restPictureURL)
                reviewImageView.setImageURI(myUri)
            }

        }
    }




    override fun onStop() {
        super.onStop()
        /*
        (applicationContext as FoodMapApplication).applicationScope.launch {
            (applicationContext as FoodMapApplication).repository.updateGeoPhotoCaption(markerID, captionText.text.toString())
        } */

    }
    companion object {
    }
}