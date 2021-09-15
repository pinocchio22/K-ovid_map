package com.example.k_ovid_map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var lats: ArrayList<String>
    lateinit var lngs: ArrayList<String>
    lateinit var names: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        lats = intent.getStringArrayListExtra("lats")!!
        lngs = intent.getStringArrayListExtra("lngs")!!
        names = intent.getStringArrayListExtra("name")!!
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        var latlng : LatLng? = null
        for(i in 0..lats.size-1){
            latlng = LatLng(lats[i].toDouble(),lngs[i].toDouble())
            mMap.addMarker(MarkerOptions().position(latlng).title(names[i]))

        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng))
    }
}