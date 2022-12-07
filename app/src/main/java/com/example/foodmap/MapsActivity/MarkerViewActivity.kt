package com.example.foodmap.MapsActivity

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import com.example.foodmap.R

class MarkerViewActivity : AppCompatActivity() {
    /*
    private var markerID: Int = -1
    private lateinit var captionText: EditText
    private lateinit var imageView: ImageView
    /*private val mapsViewModel: MapsViewModel by viewModels {
        MapsViewModel.ToDoListViewModelFactory((application as GeoPhotoApplication).repository)
    } */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marker_view)
        Log.d("MarkerView", "In MarkerViewActivity")

        val markerIDString = intent.getStringExtra("ID").toString()
        markerID = markerIDString.toInt()

        //captionText = findViewById(R.id.imageCaption)
        //imageView = findViewById(R.id.imageView)

        Log.d("imageView dimensions:", "" + imageView.getWidth() + " " + imageView.getHeight())

        setCaption()
        setPicture()
    }

    fun setPicture() {
        var currentPhotoPath = ""

        (applicationContext as GeoPhotoApplication).applicationScope.launch {
            currentPhotoPath =
                (applicationContext as GeoPhotoApplication).repository.getGeoPhotoFilePathById(markerID)
            Log.d("Alpha", "In activity, imagePath is " + currentPhotoPath)
            // Get the dimensions of the View
            val targetW: Int = 500//imageView.getWidth()
            val targetH: Int = 300//imageView.getHeight()
            Log.d("MVA", "imageView:"+ targetW + " " + targetH)

            // Get the dimensions of the bitmap
            Log.d("MVA", "Photopath:"+ currentPhotoPath)
            val bmOptions = BitmapFactory.Options()
            bmOptions.inJustDecodeBounds = true
            BitmapFactory.decodeFile(currentPhotoPath, bmOptions)


            val photoW = bmOptions.outWidth
            val photoH = bmOptions.outHeight

            // Determine how much to scale down the image
            Log.d("MVA", "Photo: " + photoW + " " + photoH)
            val scaleFactor = Math.max(1, Math.min(photoW / targetW, photoH / targetH))

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false
            bmOptions.inSampleSize = scaleFactor
            val bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions)
            //imageView.setImageBitmap(bitmap)
            runOnUiThread {
                imageView.setImageBitmap(bitmap)
            }
        }

    }

    fun setCaption() {
        var imageCaption = ""
        (applicationContext as GeoPhotoApplication).applicationScope.launch {
            imageCaption =
                (applicationContext as GeoPhotoApplication).repository.getGeoPhotoCaptionById(
                    markerID
                )
            Log.d("Alpha", "In activity, imageCaption is " + imageCaption)
            captionText.setText(imageCaption)
        }
    }


    override fun onStop() {
        super.onStop()
        (applicationContext as GeoPhotoApplication).applicationScope.launch {
            (applicationContext as GeoPhotoApplication).repository.updateGeoPhotoCaption(markerID, captionText.text.toString())
        }

    }
    companion object {
    } */
}