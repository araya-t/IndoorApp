package com.estimote.indoorapp

import android.app.Notification
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.estimote.indoorsdk.IndoorLocationManagerBuilder
import com.estimote.indoorsdk_module.algorithm.OnPositionUpdateListener
import com.estimote.indoorsdk_module.algorithm.ScanningIndoorLocationManager
import com.estimote.indoorsdk_module.cloud.Location
import com.estimote.indoorsdk_module.cloud.LocationPosition
import com.estimote.indoorsdk_module.view.IndoorLocationView
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory
import java.util.function.Consumer

/**
 * Main view for indoor location
 */

class MainActivity : AppCompatActivity() {

    private lateinit var indoorLocationView: IndoorLocationView
    private lateinit var indoorLocationManager: ScanningIndoorLocationManager
    private lateinit var location: Location
    private lateinit var notification: Notification
    private lateinit var tvPositionXY: TextView
    private lateinit var onPositionUpdateListener: OnPositionUpdateListener
    private lateinit var locaPo: LocationPosition
    private var listLoca = ArrayList<LocationPosition>()
//    val list = ArrayList<String>()



    companion object {
        val intentKeyLocationId = "location_id"
        fun createIntent(context: Context, locationId: String): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(intentKeyLocationId, locationId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Declare notification that will be displayed in user's notification bar.
        // You can modify it as you want/
        notification = Notification.Builder(this)
                .setSmallIcon(R.drawable.beacon_gray)
                .setContentTitle("Estimote Inc. \u00AE")
                .setContentText("Indoor location is running...")
                .setPriority(Notification.PRIORITY_HIGH)
                .build()

        // Get location id from intent and get location object from list of locations
        setupLocation()

        // ------ Init tvPositionXY

        tvPositionXY = findViewById(R.id.tvPositionXY)

        // Init indoor location view here
        indoorLocationView = findViewById(R.id.indoor_view)

        // Give location object to your view to draw it on your screen
        indoorLocationView.setLocation(location)

        // Create IndoorManager object.
        // Long story short - it takes list of scanned beacons, does the magic and returns estimated position (x,y)
        // You need to setup it with your app context,  location data object,
        // and your cloud credentials that you declared in IndoorApplication.kt file
        // we are using .withScannerInForegroundService(notification)
        // this will allow for scanning in background and will ensura that the system won't kill the scanning.
        // You can also use .withSimpleScanner() that will be handled without service.
        indoorLocationManager = IndoorLocationManagerBuilder(this, location, (application as IndoorApplication).cloudCredentials)
                .withScannerInForegroundService(notification)
                .build()

        // Hook the listener for position update events


        fun saveData(locaPo: LocationPosition){
            this.locaPo = locaPo
            listLoca.add(this.locaPo)
            Log.i("Position", "LocaPo x = " + locaPo.x + " , y = " + locaPo.y)
            println("In save data--> list Loca size = " + listLoca.size)
        }

        onPositionUpdateListener = object : OnPositionUpdateListener {
            override fun onPositionOutsideLocation() {
                indoorLocationView.hidePosition()
            }

            override fun onPositionUpdate(locationPosition: LocationPosition) {
                saveData(locationPosition)
                indoorLocationView.updatePosition(locationPosition)
//                Log.i("Position", "x = " + locationPosition.x + " , y = " + locationPosition.y)
//                Log.i("Position", "LocaPo x = " + locaPo.x + " , y = " + locaPo.y)
//                tvPositionXY.setText("X:" + locaPo.x + "  Y:" + locaPo.y)
            }
        }

        indoorLocationManager.setOnPositionUpdateListener(onPositionUpdateListener)


        // Check if bluetooth is enabled, location permissions are granted, etc.
        RequirementsWizardFactory.createEstimoteRequirementsWizard()
                .fulfillRequirements(this,
                        onRequirementsFulfilled = {
                            indoorLocationManager.startPositioning()
                        },
                        onRequirementsMissing = {
                            Toast.makeText(applicationContext, "Unable to scan for beacons. Requirements missing: ${it.joinToString()}", Toast.LENGTH_SHORT)
                                    .show()
                        },
                        onError = {
                            Toast.makeText(applicationContext, "Unable to scan for beacons. Error: ${it.message}", Toast.LENGTH_SHORT)
                                    .show()
                        })

    }

    private fun setupLocation() {
        // get id of location to show from intent
        val locationId = intent.extras.getString(intentKeyLocationId)
        // get object of location. If something went wrong, we build empty location with no data.
        location = (application as IndoorApplication).locationsById[locationId] ?: buildEmptyLocation()
        // Set the Activity title to you location name
        title = location.name
    }

    private fun buildEmptyLocation(): Location {
        return Location("", "", true, "", 0.0, emptyList(), emptyList(), emptyList())
    }


    override fun onDestroy() {
        indoorLocationManager.stopPositioning()
        super.onDestroy()
    }
//
//    override fun onStart() {
//        super.onStart()
//    }
//
//    override fun onResume() {
//        super.onResume()
//    }

    override fun onPause() {
        super.onPause()

        var i = 0;
        for (item in listLoca) {
            i = i+1
            print("From(size:"+listLoca.size +") i=" + i + "--> x:" + item.x + " y:" + item.y + "\n")
        }
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        // save everything in here

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        // restore everything in here
    }
}
