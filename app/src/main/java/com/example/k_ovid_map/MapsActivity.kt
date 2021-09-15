package com.example.k_ovid_map

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Address
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var lats: ArrayList<String>
    lateinit var lngs: ArrayList<String>
    lateinit var names: ArrayList<String>

    lateinit var tag_marker : TextView
    lateinit var marker_view : View
    var selectedMarker: Marker? = null


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
            latlng = LatLng(lats[i].toDouble(), lngs[i].toDouble())
            mMap.addMarker(MarkerOptions().position(latlng).title(names[i]))

        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng))

        mMap.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener {
            override fun onMarkerClick(marker: Marker): Boolean {
                val center: CameraUpdate = CameraUpdateFactory.newLatLng(marker.getPosition())
                mMap.animateCamera(center)
                changeSelectedMarker(marker)
                return true
            }
        })

        mMap.setOnMapClickListener(object : GoogleMap.OnMapClickListener {
            override fun onMapClick(p0: LatLng?) {
                changeSelectedMarker(null);
            }
        })
    }

    private fun setCustomMarkerView() {
        var marker_view = LayoutInflater.from(this).inflate(R.layout.marker, null)
        tag_marker = marker_view.findViewById(R.id.tag_marker) as TextView
    }

    private fun addMarker(latlngdata: markerItem, isSelectedMarker: Boolean): Marker {

        var position = LatLng(latlngdata.lat, latlngdata.lng)
        var list: List<Address>? = null
        var markerOptions = MarkerOptions()
        if (isSelectedMarker) {
            tag_marker.setBackgroundResource(R.drawable.ic_marker_phone_blue)
            tag_marker.setTextColor(Color.WHITE)
        } else {
            tag_marker.setBackgroundResource(R.drawable.ic_marker_phone_blue)
            tag_marker.setTextColor(Color.BLACK)
        }

        markerOptions.position(position)
        tag_marker.setText(latlngdata.tag)
        markerOptions.icon(
            BitmapDescriptorFactory.fromBitmap(
                createDrawableFromView(
                    this, marker_view
                )
            )
        )
        return mMap.addMarker(markerOptions)
    }

    private fun createDrawableFromView(context: Context, view: View): Bitmap {
        val displayMetrics = DisplayMetrics()
        (context as Activity).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
        view.setLayoutParams(
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.buildDrawingCache()
        val bitmap: Bitmap = Bitmap.createBitmap(
            view.getMeasuredWidth(),
            view.getMeasuredHeight(),
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun changeSelectedMarker(marker: Marker?) {
        // 선택했던 마커 되돌리기
        if(selectedMarker != marker) {
            addMarker(selectedMarker!!, false)
            selectedMarker!!.remove()

            // 선택한 마커 표시
            if (marker != null) {
                addMarker(marker, true)
                marker.remove()
            }
            else
                selectedMarker = null
        }
    }

    private fun addMarker(marker: Marker, isSelectedMarker: Boolean): Marker {
        val lat: Double = marker.getPosition().latitude
        val lon: Double = marker.getPosition().longitude
        val tag: String = marker.getTitle()
        val temp = markerItem(lat, lon, tag)
        return addMarker(temp, isSelectedMarker)
    }
}