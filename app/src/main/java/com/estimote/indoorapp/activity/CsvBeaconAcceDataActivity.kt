package com.estimote.indoorapp.activity

import android.app.Notification
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import com.estimote.indoorapp.BeaconApplication
import com.estimote.indoorapp.model.CsvReader
import com.estimote.indoorapp.utils.CsvRow
import com.estimote.indoorapp.model.CsvWriter
import com.estimote.indoorapp.R
import com.estimote.indoorapp.view.AccelerometerDataViewGroup
import com.estimote.indoorapp.view.ChangeGmsStatusViewGroup
import com.estimote.indoorapp.view.StartStopButtonViewGroup
import com.estimote.indoorapp.view.StopEngineButtonViewGroup
import com.estimote.indoorsdk.IndoorLocationManagerBuilder
import com.estimote.indoorsdk_module.algorithm.OnPositionUpdateListener
import com.estimote.indoorsdk_module.algorithm.ScanningIndoorLocationManager
import com.estimote.indoorsdk_module.cloud.Location
import com.estimote.indoorsdk_module.cloud.LocationPosition
import com.estimote.indoorsdk_module.view.IndoorLocationView
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory
import java.io.File

import java.io.IOException
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class CsvBeaconAcceDataActivity : AppCompatActivity() , View.OnClickListener {
    private lateinit var notification: Notification
    private lateinit var location: Location
    private lateinit var indoorLocationManager: ScanningIndoorLocationManager
    private lateinit var onPositionUpdateListener: OnPositionUpdateListener
    private lateinit var indoorLocationView: IndoorLocationView

    private lateinit var locationPos : LocationPosition
    private var locationPosition_x:Double = 0.0
    private var locationPosition_y:Double = 0.0
    private var num = 1;

    private var sensorManager: SensorManager? = null
    private var accelSensor: Sensor? = null
    private lateinit var accelerometerDataViewGroup: AccelerometerDataViewGroup
    private lateinit var startStopButtonViewGroup: StartStopButtonViewGroup
    private lateinit var stopEngineButtonViewGroup: StopEngineButtonViewGroup
    private lateinit var changeGmsStatusViewGroup: ChangeGmsStatusViewGroup
    private lateinit var tvX_position: TextView
    private lateinit var tvY_position: TextView
    private lateinit var stillButton: Button
    private val dcm = DecimalFormat("0.000000")
    private val dcmBeacon = DecimalFormat("0.0000")
    private val sdf = SimpleDateFormat("yyyy-MM-dd_HH:mm:ss")
    private val sdfTimeStamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSSSSS")

    private var startTime: Long = 0
    private var fileName: String? = null
    private var directory: String = ""
    private lateinit var csvWriter: CsvWriter
    private var isStill: Boolean = false
    private var isStopEngine: Boolean = false
    private var isChangeGmsStatus: Boolean = false
    private var timeStampAcce: Long = 0
    private var milliSecAcce:Long = 0
    private var listenerSampling = -1

    private lateinit var csvReader: CsvReader
    private var isReadFinish: Boolean = false

/**----------------------------------------------------------------------------------------------**/
    companion object {
        val intentKeyLocationId = "location_id"
        fun createIntent(context: Context, locationId: String): Intent {
            val intent = Intent(context, CsvBeaconAcceDataActivity::class.java)
            intent.putExtra(intentKeyLocationId, locationId)

            Log.i("LocationID","location id: "+ locationId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_csv_beacon_acce_data)

        Log.d("CsvBeaconAcceData Acti","--------- IN HEREEEEEEEEEEEEEEE---------")

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        notification = Notification.Builder(this)
                .setSmallIcon(R.drawable.beacon_gray)
                .setContentTitle("Estimote Inc. \u00AE")
                .setContentText("Indoor location is running...")
                .setPriority(Notification.PRIORITY_HIGH)
                .build()
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        setupLocation()
        initInstances()

        // Give location object to your view to draw it on your screen
        indoorLocationView.setLocation(location)

        startStopButtonViewGroup.getBtnStart().setOnClickListener(this)
        startStopButtonViewGroup.btnStop.setOnClickListener(this)
        accelerometerDataViewGroup.btnEnter.setOnClickListener(this)
        stopEngineButtonViewGroup.btnStopEngine.setOnClickListener(this)
        changeGmsStatusViewGroup.btnChangeGmsStatus.setOnClickListener(this)
        stillButton.setOnClickListener(this)

        Toast.makeText(this, "You can set listener sampling rate", Toast.LENGTH_SHORT).show()

        /* Create IndoorManager object.
        Long story short - it takes list of scanned beacons, does the magic and returns estimated position (x,y)
        You need to setup it with your app context,  location data object,
        and your cloud credentials that you declared in BeaconApplication.kt file
        we are using .withScannerInForegroundService(notification)
        this will allow for scanning in background and will ensura that the system won't kill the scanning.
        You can also use .withSimpleScanner() that will be handled without service. */
        indoorLocationManager = IndoorLocationManagerBuilder(this, location, (application as BeaconApplication).cloudCredentials)
                .withScannerInForegroundService(notification)
                .build()

/** ------------ Hook the listener for position update events -----------------------------------**/

        onPositionUpdateListener = object : OnPositionUpdateListener {
            override fun onPositionOutsideLocation() {
                indoorLocationView.hidePosition()
            }

            override fun onPositionUpdate(locationPosition: LocationPosition) {
                /** code for updateing view**/
//                println(" ================================================= in onPositionUpdate =====================================")
                indoorLocationView.updatePosition(locationPosition)
//                locationPos = locationPosition
                locationPosition_x = locationPosition.x + 17
                locationPosition_y = locationPosition.y

//                Log.i("Position",
//                        "(" + num++ + ") timestamp(" + System.currentTimeMillis()
//                                + ")x = " + locationPosition.x + " , y = " + locationPosition.y)
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

    override fun onClick(v: View) {
        if (v === startStopButtonViewGroup.btnStart) {
            startRecording()
            Toast.makeText(this, "Start recording\n with listener sampling $listenerSampling", Toast.LENGTH_SHORT).show()
        }

        if (v === startStopButtonViewGroup.btnStop) {
            stopRecording()
            Toast.makeText(this, "Stop recording", Toast.LENGTH_SHORT).show()
            isSaveFileSuccess()
        }

        if (v === accelerometerDataViewGroup.btnEnter) {
            // set unregisterListener brfore set new listenerSampling
            // then register registerListener again with new listenerSampling

            unregisterListener()

            val listenerSamplingStr = accelerometerDataViewGroup.editTextListenerSampling.text.toString()
            listenerSampling = Integer.parseInt(listenerSamplingStr)

            val isSuccess = registerListener()
            accelerometerDataViewGroup.setEditTextListenerSampling(listenerSamplingStr)

            if (isSuccess) {
                Toast.makeText(this, "Listener sampling rate = " + listenerSampling, Toast.LENGTH_SHORT).show()
            }
        }

        if (v === stopEngineButtonViewGroup.btnStopEngine) {
            isStopEngine = true
            Toast.makeText(this, "Stop engine", Toast.LENGTH_SHORT).show()
        }

        if (v === changeGmsStatusViewGroup.btnChangeGmsStatus) {
            isChangeGmsStatus = true
            Toast.makeText(this, "Change GMS status", Toast.LENGTH_SHORT).show()
        }

        if (v === stillButton) {
            isStill = true
            Toast.makeText(this, "Still", Toast.LENGTH_SHORT).show()
        }

    }

    fun isSaveFileSuccess(){
        val fileDirectory = "BeaconSensorCsvFile"
        val fileToGet = File(fileDirectory, fileName)

        if(fileToGet != null){
            Toast.makeText(this@CsvBeaconAcceDataActivity,
                    "Save file SUCCESS", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this@CsvBeaconAcceDataActivity,
                    "Save file FAILED", Toast.LENGTH_SHORT).show()
        }
    }

    fun initInstances() {

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager?
        accelSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        accelerometerDataViewGroup = findViewById(R.id.accelerometerDataViewGroup)
        startStopButtonViewGroup = findViewById(R.id.startStopButtonViewGroup)
        stopEngineButtonViewGroup = findViewById(R.id.stopEngineButtonViewGroup)
        changeGmsStatusViewGroup = findViewById(R.id.changeGmsStatusViewGroup)
        tvX_position = findViewById(R.id.tvX_position)
        tvY_position = findViewById(R.id.tvY_position)
        stillButton = findViewById(R.id.stillButton)

        // Init indoor location view here
        indoorLocationView = findViewById(R.id.indoor_view)

        csvWriter = CsvWriter()
        csvReader = CsvReader()

        locationPosition_x = 0.0
        locationPosition_y = 0.0
        isChangeGmsStatus = false
        isStopEngine = false
        isReadFinish = false
        isStill = false
        fileName = null
        directory = Environment.getExternalStorageDirectory().toString() +
                    "/_Parka/BeaconSensorCsvFile"

//        locationPos = LocationPosition()
    }

/** ---------------------- Set up Location ----------------------------------------------------- **/

    private fun setupLocation() {
        // get id of location to show from intent
        val locationId = intent.extras.getString(intentKeyLocationId)
        // get object of location. If something went wrong, we build empty location with no data.
        location = (application as BeaconApplication).locationsById[locationId] ?: buildEmptyLocation()
        // Set the Activity title to you location name
        title = location.name
    }

    private fun buildEmptyLocation(): Location {
        return Location("", "", true, "", 0.0, emptyList(), emptyList(), emptyList())
    }

/** ------------------------ Sensor ------------------------------------------------------------ **/
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
            val acc_z_formatted = dcm.format(acc_z)

            milliSecAcce = System.currentTimeMillis() - startTime
            timeStampAcce = System.currentTimeMillis()




            accelerometerDataViewGroup.setTvAccel_x_text("X : " + acc_x_formatted)
            accelerometerDataViewGroup.setTvAccel_y_text("Y : " + acc_y_formatted)
            accelerometerDataViewGroup.setTvAccel_z_text("Z : " + acc_z_formatted)

            tvX_position.setText(dcmBeacon.format(locationPosition_x))
            tvY_position.setText(dcmBeacon.format(locationPosition_y))

            Log.i("Write Sensor Data",
                    "AcceData: (" + milliSecAcce + ") " +
                            "[" + timeStampAcce + "] x="
                            + acc_x + " ,y=" + acc_y + " ,z=" + acc_z
                            + "\n Beacon [x: "
                            + locationPosition_x + " , y:"+locationPosition_y );

            val line = (milliSecAcce.toString() + ","
                    + sdfTimeStamp.format(timeStampAcce) + ","
                    + acc_x_formatted + ","
                    + acc_y_formatted + ","
                    + acc_z_formatted + ","
                    + isStill + ","
                    + isStopEngine  + ","
                    + locationPosition_x + ","
                    + locationPosition_y + ","
                    + isChangeGmsStatus + ","
                    + "\n")

            if (csvWriter.file != null) {
                csvWriter.writeToFile(line)
            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
            Log.i("onAccuracyChanged"," -------> accuracy = " + accuracy)
        }
    }

/** -------------- Start recording ------------------------------------------------------ **/
    private fun startRecording() {
        // Prepare title data of file
        val now = Date(System.currentTimeMillis())
        fileName = "BeaconAccelerometerData_" + sdf.format(now) +
                        "_sampling_" + listenerSampling +
                        "microsec.csv"
        println("File name = "+fileName)

        csvWriter.createFile(fileName, directory)
        startTime = csvWriter.startTime

        val headFileStr = ("millisec" + "," + "timeStamp" + ","
                            + "acce_X" + "," + "acce_Y" + "," + "acce_Z" + ","
                            + "is_still" + ","
                            + "is_stop_engine" + ","
                            + "x_position" + ","
                            + "y_position" + ","
                            + "is_change_gms_status" + ","
                            + "\n")
        csvWriter.writeHeadFile(headFileStr)

        registerListener()
        Toast.makeText(this, "START RECORDING | "
                + "file name = " + fileName, Toast.LENGTH_SHORT).show()
    }

/** -------------- Stop recording -------------------------------------------------------- **/
    private fun stopRecording() {
        unregisterListener()

        if (csvWriter.file != null) {
            csvWriter.closeFile()
            isReadFinish = false
            isChangeGmsStatus = false
            isStopEngine = false
            isStill = false
        }

        if (isReadFinish != true) {
            var csvRows: MutableList<CsvRow> = ArrayList()
            try {
                if (fileName != null) {
                    csvRows = csvReader.readCsv("BeaconSensorCsvFile/",fileName);
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            if (csvRows.size != 0) {
                var i = 1
                while (i < csvRows.size) {
                    Log.d("read from csv file",
                            String.format("row %s|%s: %s, %s, %s, %s, %s, %s, %s ,%s",
                                    i + 1, csvRows[i].countRow, csvRows[i].millisec, csvRows[i].timeStamp, csvRows[i].acce_x
                                    , csvRows[i].acce_y, csvRows[i].acce_z, csvRows[i].is_stop_engine
                                    , csvRows[i].x_position, csvRows[i].y_position))
                    i++
                }

                if (i == csvRows.size) {
                    isReadFinish = true
                    csvRows.clear()
                }
            }
        }
    }

/** ------------------------------- Each state of Activity ------------------------------------- **/

    override fun onResume() {
        super.onResume()
        registerListener()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterListener()
        if (csvWriter.file != null) {
            stopRecording()
        }

        isStopEngine = false
        isChangeGmsStatus = false
    }
}
