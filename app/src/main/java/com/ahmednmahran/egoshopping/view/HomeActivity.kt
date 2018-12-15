package com.ahmednmahran.egoshopping.view

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.ahmednmahran.egoshopping.R
import com.ahmednmahran.egoshopping.controller.location.AddressResultReceiver
import com.ahmednmahran.egoshopping.controller.location.FetchAddressIntentService
import com.ahmednmahran.egoshopping.controller.location.FetchAddressIntentService.*
import com.ahmednmahran.egoshopping.controller.settings.AppPreference
import com.ahmednmahran.egoshopping.model.Product
import com.ahmednmahran.egoshopping.model.User
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.toast

class HomeActivity : AppCompatActivity(), OnMapReadyCallback, AddressResultReceiver.Receiver{

    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        // Display the address string
        // or an error message sent from the intent service.
        addressOutput = resultData?.getString(Constants.RESULT_DATA_KEY) ?: ""
        updateUI(addressOutput)
        // Show a toast message if an address was found.
        if (resultCode == Constants.SUCCESS_RESULT) {
            toast(getString(R.string.address_found))
        }

    }

    private var lastKnownLocation: Location? = Location("")
    private var resultReceiver: AddressResultReceiver? = null
    private var requestingLocationUpdates: Boolean = false
    private val REQUESTING_LOCATION_UPDATES_KEY = "request_updates"
    private var addressOutput = ""
    private lateinit var savedUser: User
    private lateinit var savedProduct: Product
    private lateinit var mMap: GoogleMap


    private fun startIntentService() {
        val intent = Intent(this, FetchAddressIntentService::class.java).apply {
            putExtra(Constants.RECEIVER, resultReceiver)
            putExtra(Constants.LOCATION_DATA_EXTRA, lastKnownLocation)
        }
        startService(intent)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val store = savedProduct.store.geolocation
        mMap.addMarker(MarkerOptions().position(store).title(savedProduct.store.name))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(store))
        mMap.setOnMapClickListener {

            mMap.clear()
            mMap.addMarker(MarkerOptions().position(it))
            lastKnownLocation?.latitude = it.latitude
            lastKnownLocation?.longitude = it.longitude
            fetchAddressButtonHandler()
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_map -> {
                loadMap()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_checkout -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val appPreference = AppPreference.getInstance()
        resultReceiver = AddressResultReceiver(Handler())
        resultReceiver?.setReceiver(this)
        savedUser = appPreference.savedUser
        savedProduct = appPreference.savedProduct
        val geolocation = savedProduct.store.geolocation
        lastKnownLocation?.latitude = geolocation.latitude
        lastKnownLocation?.longitude = geolocation.longitude
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, requestingLocationUpdates)
        super.onSaveInstanceState(outState)
    }

    private fun loadMap(){
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    private fun fetchAddressButtonHandler() {
            // Start service and update UI to reflect new location
            startIntentService()
    }

    private fun updateUI(address: String){
        toast(address)
        supportActionBar?.subtitle = address
    }
}
