package com.example.foodmap.MapsActivity

import android.Manifest
import android.app.Activity
import android.location.Location
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.foodmap.R
import com.example.foodmap.replaceFragmentInActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MapsActivity : AppCompatActivity() {
    //Fragment for map control
    private lateinit var mapsFragment: OpenStreetMapFragment
    //String to use in storing last photoPath
    var currentPhotoPath: String = ""
    //Boolean to keep track of whether permissions have been granted
    private var locationPermissionEnabled:Boolean = false
    //Boolean to keep track of whether activity is currently requesting location Updates
    private var locationRequestsEnabled:Boolean = false
    //Member object for the FusedLocationProvider
    private lateinit var locationProviderClient: FusedLocationProviderClient
    //Member object for the last known location
    private lateinit var mCurrentLocation: Location
    //Member object to hold onto locationCallback object
    //Needed to remove requests for location updates
    private lateinit var mLocationCallback: LocationCallback


    val takePictureResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(applicationContext, "No picture taken", Toast.LENGTH_LONG)
        }else{
            Log.d("MainActivity","Picture Taken at location $currentPhotoPath")

        }
    }
    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Log.d("MapsActivity","Permission Granted")
            } else {
                Toast.makeText(this,"Location Permissions not granted. Location disabled on map",
                    Toast.LENGTH_LONG).show()
            }
        }

    /*
    private val mapsViewModel: MapsViewModel by viewModels {
        MapsViewModel.FoodReviewItemListViewModelFactory((application as GeoPhotoApplication).repository)
    } */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        /*
        findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener{
            Log.d("MainAcivity", "Photo button clicked")
            takeNewPhoto()
        } */

        //Get preferences for tile cache
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))

        //Get a locationProviderClient object
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        //Attempt to get the last known location
        //Will either require permission check or will return last known location
        //through the locationUtilCallback
        getLastLocation(this,locationProviderClient,locationUtilCallback)

        //Get access to mapsFragment object
        mapsFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
                as OpenStreetMapFragment? ?:OpenStreetMapFragment.newInstance().also{
            replaceFragmentInActivity(it,R.id.fragmentContainerView)
        }


        //Begin observing data changes
        /*
        mapsViewModel.allGeoPhoto.observe(this){
                geoPhotos->
            geoPhotos.let {
                for(photo in geoPhotos){
                    val latitude = photo.value.latitude
                    val longitude = photo.value.longitude
                    val id = photo.value.id
                    var geoPoint: GeoPoint? = null

                    if(latitude!=null){
                        if(longitude!= null){
                            geoPoint = GeoPoint(latitude,longitude)
                        }
                    }
                    if(id != null && geoPoint!= null){
                        mapsFragment.addMarker(geoPoint,id)
                    }
                }
            }
        } */


    }

    override fun onStart() {
        super.onStart()
        //Start location updates
        startLocationRequests()
    }

    override fun onStop(){
        super.onStop()
        //if we are currently getting updates
        if(locationRequestsEnabled){
            //stop getting updates
            locationRequestsEnabled = false
            stopLocationUpdates(locationProviderClient,mLocationCallback)
        }
    }

    private fun startLocationRequests(){
        //If we aren't currently getting location updates
        if(!locationRequestsEnabled){
            //create a location callback
            mLocationCallback = createLocationCallback(locationUtilCallback)
            //and request location updates, setting the boolean equal to whether this was successful
            locationRequestsEnabled = createLocationRequest(this,locationProviderClient,mLocationCallback)
        }
    }
    /*
    private fun takeNewPhoto(){

        //Collect currentPhotoPath
        Log.d("MainActivity","Entered takeNewPhoto")
        val picIntent = Intent().setAction(MediaStore.ACTION_IMAGE_CAPTURE)
        if (picIntent.resolveActivity(packageManager) != null){
            val filepath: String = createFilePath()
            val myFile: File = File(filepath)
            currentPhotoPath = filepath
            val photoUri = FileProvider.getUriForFile(this,"edu.uark.ahnelson.assignment3solution.fileprovider",myFile)
            picIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri)
            takePictureResultLauncher.launch(picIntent)
        }


        var newGeoPhoto:GeoPhoto = GeoPhoto(null, currentPhotoPath, mCurrentLocation.latitude, mCurrentLocation.longitude, 9001.00, "Description...")
        Log.d("MainActivity", newGeoPhoto.toString())
        (applicationContext as GeoPhotoApplication).applicationScope.launch {
            (applicationContext as GeoPhotoApplication).repository.insert(newGeoPhoto)
        }


        /*(applicationContext as GeoPhotoApplication).applicationScope.launch {
            val intent = Intent(applicationContext, MarkerViewActivity::class.java)
            val newPhotoID: Int =
                (applicationContext as GeoPhotoApplication).repository.getGeoPhotoId(
                    currentPhotoPath
                )
            intent.putExtra("ID", newPhotoID.toString())
            startActivity(intent)
        }*/
    } */

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



    /*private fun checkForLocationPermission(){
        when {
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                //getLastKnownLocation()
                //registerLocationUpdateCallbacks()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }*/

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            //If successful, startLocationRequests
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                locationPermissionEnabled = true
                startLocationRequests()
            }
            //If successful at coarse detail, we still want those
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                locationPermissionEnabled = true
                startLocationRequests()
            } else -> {
            //Otherwise, send toast saying location is not enabled
            locationPermissionEnabled = false
            Toast.makeText(this,"Location Not Enabled", Toast.LENGTH_LONG)
        }
        }
    }

    //LocationUtilCallback object
    //Dynamically defining two results from locationUtils
    //Namely requestPermissions and locationUpdated
    private val locationUtilCallback = object:LocationUtilCallback{
        //If locationUtil request fails because of permission issues
        //Ask for permissions
        override fun requestPermissionCallback() {
            locationPermissionRequest.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION))
        }
        //If locationUtil returns a Location object
        //Populate the current location and log
        override fun locationUpdatedCallback(location: Location) {
            mCurrentLocation = location
            val lat = (location.latitude * 1E6).toInt()
            val lng = (location.longitude * 1E6).toInt()
            val point = GeoPoint(lat, lng)
            mapsFragment.changeCenterLocation(point)
            Log.d("MainActivity","Location is [Lat: ${location.latitude}, Long: ${location.longitude}]")
        }
    }
}