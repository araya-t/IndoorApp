package com.estimote.indoorapp.Activity.IndoorLocation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.estimote.indoorapp.Model.IndoorLocation.BeaconApplication
import com.estimote.indoorapp.Adapter.IndoorLocation.LocationListAdapter
import com.estimote.indoorapp.R
import com.estimote.indoorsdk_module.cloud.Location

/**
 * This is a simple activity to display all locations in list view.
 * You can modify it freely :)
 */

class LocationListActivity : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: LocationListAdapter
    private lateinit var mNoLocationsView: TextView
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_list)
        mNoLocationsView = findViewById(R.id.no_locations_view)
        mRecyclerView = findViewById(R.id.my_recycler_view)
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager
        mRecyclerView.layoutManager = mLayoutManager
        mAdapter = LocationListAdapter(emptyList<Location>())
        mRecyclerView.adapter = (mAdapter)

        /** if click at Location will start MainActivity **/
        mAdapter.setOnClickListener { locationId ->
            startActivity(MainActivity.createIntent(this, locationId))
        }
    }

    override fun onStart() {
        super.onStart()
        val locations = (application as BeaconApplication).locationsById.values.toList()
        if (locations.isEmpty()) {
            mNoLocationsView.visibility = View.VISIBLE
            mRecyclerView.visibility = View.GONE
        } else {
            mAdapter.setLocations((application as BeaconApplication).locationsById.values.toList())
        }
    }
}
