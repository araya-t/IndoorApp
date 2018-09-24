package com.estimote.indoorapp.Controller.IndoorLocation

import android.app.Notification
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.estimote.indoorapp.Model.Parka.BeaconFileWriter
import com.estimote.indoorapp.Model.IndoorLocation.BeaconApplication
import com.estimote.indoorapp.Model.Parka.CsvReader
import com.estimote.indoorapp.Model.Parka.CsvWriter
import com.estimote.indoorapp.R
import com.estimote.indoorapp.View.AccelerometerDataViewGroup
import com.estimote.indoorapp.View.StartStopButtonViewGroup
import com.estimote.indoorapp.View.StopEngineButtonViewGroup
import com.estimote.indoorsdk.IndoorLocationManagerBuilder
import com.estimote.indoorsdk_module.algorithm.OnPositionUpdateListener
import com.estimote.indoorsdk_module.algorithm.ScanningIndoorLocationManager
import com.estimote.indoorsdk_module.cloud.Location
import com.estimote.indoorsdk_module.cloud.LocationPosition
import com.estimote.indoorsdk_module.view.IndoorLocationView
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory
import java.text.DecimalFormat
import java.text.SimpleDateFormat

/**
 * Main view for indoor location
 */

class MainActivity : AppCompatActivity() {
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */

    private lateinit var indoorLocationView: IndoorLocationView
    private lateinit var indoorLocationManager: ScanningIndoorLocationManager
    private lateinit var location: Location
    private lateinit var notification: Notification
    private var listLocationPosition = ArrayList<LocationPosition>()
    private lateinit var onPositionUpdateListener: OnPositionUpdateListener
    private lateinit var beaconFileWriter: BeaconFileWriter
    var isStartRecording = false

    private lateinit var locationPos : LocationPosition
    private var locationPosition_x = "0"
    private var locationPosition_y = "0"
    private var num = 1;

    private var sensorManager: SensorManager? = null
    private var accelSensor: Sensor? = null
    private lateinit var accelerometerDataViewGroup: AccelerometerDataViewGroup
    private lateinit var startStopButtonViewGroup: StartStopButtonViewGroup
    private lateinit var stopEngineButtonViewGroup: StopEngineButtonViewGroup
    private val dcm = DecimalFormat("0.000000")
    private val sdf = SimpleDateFormat("yyyy-MM-dd_HH:mm:ss")
    private val sdfTimeStamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSSSSS")

    private var startTime: Long = 0
    private var fileName: String? = null
    private lateinit var csvWriter: CsvWriter
    private var isStopEngine: Boolean = false
    private var timeStampAcce: Long = 0
    private var milliSecAcce:Long = 0
    private var listenerSampling = -1

    private lateinit var csvReader: CsvReader
    private var isReadFinish: Boolean = false





    //    lateinit var btnStart: Button
//    lateinit var btnStop: Button
    //    private lateinit var locaPo: LocationPosition
//    private lateinit var tvPositionXY: TextView
//    private lateinit var file: BufferedWriter
    //    private var dcm = DecimalFormat("0.0000")

    companion object {
        val intentKeyLocationId = "location_id"
        fun createIntent(context: Context, locationId: String): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(intentKeyLocationId, locationId)

            Log.i("LocationID","location id: "+ locationId)
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
        initInstances()

        beaconFileWriter.startRecording()
        isStartRecording = true;

        /** Create IndoorManager object.
            Long story short - it takes list of scanned beacons, does the magic and returns estimated position (x,y)
            You need to setup it with your app context,  location data object,
            and your cloud credentials that you declared in BeaconApplication.kt file
            we are using .withScannerInForegroundService(notification)
            this will allow for scanning in background and will ensura that the system won't kill the scanning.
            You can also use .withSimpleScanner() that will be handled without service. **/
        indoorLocationManager = IndoorLocationManagerBuilder(this, location, (application as BeaconApplication).cloudCredentials)
                .withScannerInForegroundService(notification)
                .build()


        fun saveData(locationPosition: LocationPosition) {
            listLocationPosition.add(locationPosition)
//            Log.i("Position", "LocaPo x = " + locaPo.x + " , y = " + locaPo.y)
            println("In save data--> list Loca size = " + listLocationPosition.size)
        }


/** Hook the listener for position update events **/
/* ------------SAVE DATA AT HERE----------------------------------------------------------------------
   --------------------------------------------------------------------------------------------------- */
        onPositionUpdateListener = object : OnPositionUpdateListener {
            override fun onPositionOutsideLocation() {
                indoorLocationView.hidePosition()
            }

            override fun onPositionUpdate(locationPosition: LocationPosition) {
                if (isStartRecording) {
                    saveData(locationPosition) /** code for write data to csv **/
                }
                beaconFileWriter.writeBeaconDataEachLine(locationPosition)
                locationPosition_x = locationPosition.x.toString();
                locationPosition_y = locationPosition.y.toString();

                /** code for updateing view**/
                indoorLocationView.updatePosition(locationPosition)

                Log.i("Position", "x = " + locationPosition.x + " , y = " + locationPosition.y)
            }
        }

        indoorLocationManager.setOnPositionUpdateListener(onPositionUpdateListener)


        /** Check if bluetooth is enabled, location permissions are granted, etc. **/
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

    fun initInstances() {

        // Init indoor location view here
        indoorLocationView = findViewById(R.id.indoor_view)

        // Give location object to your view to draw it on your screen
        indoorLocationView.setLocation(location)

        beaconFileWriter = BeaconFileWriter()

    }

    private fun setupLocation() {
        /** get id of location to show from intent **/
        val locationId = intent.extras.getString(intentKeyLocationId)
        /** get object of location. If something went wrong, we build empty location with no data. **/
        location = (application as BeaconApplication).locationsById[locationId] ?: buildEmptyLocation()
        /** Set the Activity title to you location name **/
        title = location.name
    }

    private fun buildEmptyLocation(): Location {
        return Location("", "", true, "", 0.0, emptyList(), emptyList(), emptyList())
    }

    override fun onPause() {
        indoorLocationManager.stopPositioning()
        beaconFileWriter.stopRecording()
        isStartRecording = false;

        super.onPause()

        var i = 0;
        for (item in listLocationPosition) {
            i = i + 1
            print("From(size:" + listLocationPosition.size + ") i=" + i + "--> x:" + item.x + " y:" + item.y + "\n")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        beaconFileWriter.stopRecording()
        isStartRecording = false;

    }

    private fun registerListener(): Boolean {
        // Register sensor listeners
        var isSuccess = false

        if (listenerSampling == -1) {
            listenerSampling = SensorManager.SENSOR_DELAY_NORMAL
        } else {
            isSuccess = true
        }
        sensorManager!!.registerListener(accelListener, accelSensor, listenerSampling)

        return isSuccess
    }

    private fun unregisterListener() {
        sensorManager!!.unregisterListener(accelListener)
    }

    private val accelListener = object : SensorEventListener {
        override fun onSensorChanged(eventAcce: SensorEvent) {
            val acc_x = eventAcce.values[0].toDouble()
            val acc_y = eventAcce.values[1].toDouble()
            val acc_z = eventAcce.values[2].toDouble()

            val acc_x_formatted = dcm.format(acc_x)
            val acc_y_formatted = dcm.format(acc_y)
            val acc_z_formatted = dcm.format(acc_y)

            milliSecAcce = System.currentTimeMillis() - startTime
            timeStampAcce = System.currentTimeMillis()


//            accelerometerDataViewGroup.setTvAccel_x_text("X : " + acc_x_formatted)
//            accelerometerDataViewGroup.setTvAccel_y_text("Y : " + acc_y_formatted)
//            accelerometerDataViewGroup.setTvAccel_z_text("Z : " + acc_z_formatted)

            Log.i("Write Sensor Data",
                    "AcceData: (" + milliSecAcce + ") " +
                            "[" + timeStampAcce + "] x="
                            + acc_x + " ,y=" + acc_y + " ,z=" + acc_z
                            + "\n Beacon [x: "
                            + locationPosition_x + " , y:"+locationPosition_y );

//            val line = (milliSecAcce.toString() + ","
//                    + sdfTimeStamp.format(timeStampAcce) + ","
//                    + acc_x_formatted + ","
//                    + acc_y_formatted + ","
//                    + acc_z_formatted + ","
//                    + isStopEngine  + ","
//                    + locationPosition_x + ","
//                    + locationPosition_y + ","
//                    + "\n")

//            if (csvWriter.file != null) {
//                csvWriter.writeToFile(line)
//            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

        }
    }
}
