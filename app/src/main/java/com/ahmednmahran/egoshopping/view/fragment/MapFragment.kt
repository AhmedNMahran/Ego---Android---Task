package com.ahmednmahran.egoshopping.view.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker

import com.ahmednmahran.egoshopping.R
import com.ahmednmahran.egoshopping.controller.location.AddressResultReceiver
import com.ahmednmahran.egoshopping.controller.location.FetchAddressIntentService
import com.ahmednmahran.egoshopping.controller.settings.AppPreference
import com.ahmednmahran.egoshopping.model.Product
import com.ahmednmahran.egoshopping.model.User
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.os.Handler
import android.support.annotation.Nullable
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.fragment_map.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MapFragment : Fragment(), OnMapReadyCallback, AddressResultReceiver.Receiver, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    private lateinit var datePicker: DatePickerFragment
    private lateinit var timePicker: TimePickerFragment
    private lateinit var date: Date
    private lateinit var lastKnownLatLng: LatLng
    private var lastKnownLocation: Location? = Location("")
    private var resultReceiver: AddressResultReceiver? = null
    private var addressOutput = ""
    private lateinit var savedUser: User
    private lateinit var savedProduct: Product
    private lateinit var mMap: GoogleMap

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int){
        date.hours = hourOfDay
        date.minutes = minute
        savedProduct.deliveryDate = date
        if(date.after(Date(2019,0,30,12,0)) && date.before(Date(2019,0,30,15,30))){
            savedProduct.deliveryCost = 0.0
        }
        else
            savedProduct.deliveryCost = 30.0
        AppPreference.getInstance().saveProduct(savedProduct)
        showSuccessUpdate()
    }

    private fun showSuccessUpdate() {
        toast(getString(R.string.data_updated))
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        date = Date(year,month,day)
        timePicker.show(childFragmentManager,"timePicker")
    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        // Display the address string
        // or an error message sent from the intent service.
        addressOutput = resultData?.getString(FetchAddressIntentService.Constants.RESULT_DATA_KEY) ?: ""
        updateUI(addressOutput)
        // Show a toast message if an address was found.
        if (resultCode == FetchAddressIntentService.Constants.SUCCESS_RESULT) {
            toast(getString(R.string.address_found))
        }

    }

    private fun startIntentService() {
        val intent = Intent(activity, FetchAddressIntentService::class.java).apply {
            putExtra(FetchAddressIntentService.Constants.RECEIVER, resultReceiver)
            putExtra(FetchAddressIntentService.Constants.LOCATION_DATA_EXTRA, lastKnownLocation)
        }
        activity?.startService(intent)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val store = savedProduct.store.geolocation
        mMap.addMarker(MarkerOptions().position(store).title(savedProduct.store.name))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(store,20f))
        mMap.setOnMapClickListener {
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(it))
            lastKnownLocation?.latitude = it.latitude
            lastKnownLocation?.longitude = it.longitude
            lastKnownLatLng= it
            fetchAddressButtonHandler()
        }
    }
    private fun updateUserAddress(latLng: LatLng, address: String){
        savedUser?.addressDescription = address
        savedUser?.geoLocation = latLng
        AppPreference.getInstance().updateUser(savedUser)
        showSuccessUpdate()

    }




    private fun loadMap(){
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }


    private fun fetchAddressButtonHandler() {
        // Start service and update UI to reflect new location
        startIntentService()
    }

    private fun updateUI(address: String){
        toast(address)
        (activity as AppCompatActivity).supportActionBar?.subtitle = address
        updateUserAddress(address = address,latLng = lastKnownLatLng)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appPreference = AppPreference.getInstance()
        resultReceiver = AddressResultReceiver(Handler())
        resultReceiver?.setReceiver(this)
        savedUser = appPreference.savedUser
        savedProduct = appPreference.savedProduct
        val geolocation = savedProduct.store.geolocation
        lastKnownLocation?.latitude = geolocation.latitude
        lastKnownLocation?.longitude = geolocation.longitude
        lastKnownLatLng = geolocation

        datePicker = DatePickerFragment()
        datePicker.setListener(this)
        timePicker = TimePickerFragment()
        timePicker.setListener(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        view.fabDate.setOnClickListener {
            try{
                datePicker.show(childFragmentManager, "datePicker")
            }catch (e:Exception){
                Log.i("DatePicker",e.message)
                e.printStackTrace()
            }
        }
        return view
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadMap()
        btnUpdateAddress.onClick {
            AppPreference.getInstance().savedUser.addressNotes = "$${getString(R.string.address_description)}: ${etDescription.text}"
            etDescription.text.clear()
            showSuccessUpdate()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         * @return A new instance of fragment MapFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = MapFragment()
    }
}
