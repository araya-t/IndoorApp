package com.estimote.indoorapp.Model.IndoorLocation

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.estimote.cloud_plugin.common.EstimoteCloudCredentials
import com.estimote.indoorapp.Manager.Contextor
import com.estimote.indoorsdk_module.cloud.Location

/**
 * START YOUR JOURNEY HERE!
 * Main app class
 */

class BeaconApplication : Application() {

    // This is map for holding all locations from your account.
    // You can move it somewhere else, but for sake of simplicity we put it in here.
    val locationsById: MutableMap<String, Location> = mutableMapOf()

    // !!! ULTRA IMPORTANT !!!
    // Change your credentials below to have access to locations from your account.
    // Make sure you have any locations created in cloud!
    // If you don't have
    // your Estimote Cloud Account - go to https://cloud.estimote.com/ and create one :)
    val cloudCredentials = EstimoteCloudCredentials("create-parking-lot-model-4db", "c5b573016d1c2efc424b5aca8b0951aa")

    override fun onCreate() {
        super.onCreate()

        //Initialize thing(s) here
        Contextor.getInstance().init(applicationContext)
    }
    override fun onTerminate() {
        super.onTerminate()
    }
}