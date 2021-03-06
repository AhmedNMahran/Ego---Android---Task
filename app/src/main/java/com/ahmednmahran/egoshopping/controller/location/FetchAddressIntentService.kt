package com.ahmednmahran.egoshopping.controller.location

import android.app.IntentService
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log
import com.ahmednmahran.egoshopping.R
import com.ahmednmahran.egoshopping.controller.settings.AppPreference
import java.io.IOException


/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * helper methods.
 */
class FetchAddressIntentService : IntentService("FetchAddressIntentServices") {
    private var receiver: ResultReceiver? = null
    object Constants {
        const val SUCCESS_RESULT = 0
        const val FAILURE_RESULT = 1
        const val PACKAGE_NAME = "com.ahmednmahran.egoshopping"
        const val RECEIVER = "$PACKAGE_NAME.RECEIVER"
        const val RESULT_DATA_KEY = "${PACKAGE_NAME}.RESULT_DATA_KEY"
        const val LOCATION_DATA_EXTRA = "${PACKAGE_NAME}.LOCATION_DATA_EXTRA"
        const val CITY_KEY = "${PACKAGE_NAME}.CITY_KEY"
    }
    val TAG = FetchAddressIntentService::class.java.simpleName

    override fun onHandleIntent(intent: Intent?) {
        val geocoder = Geocoder(this, AppPreference.getInstance().locale)
        intent ?: return

        var errorMessage = ""

        // Get the location passed to this service through an extra.
        val location = intent.getParcelableExtra<Location>(Constants.LOCATION_DATA_EXTRA)
        receiver = intent.getParcelableExtra(Constants.RECEIVER)

        var addresses: List<Address> = emptyList()

        try {
            addresses = geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                // In this sample, we get just a single address.
                1)
        } catch (ioException: IOException) {
            // Catch network or other I/O problems.
            errorMessage = getString(R.string.service_not_available)
            Log.e(TAG, errorMessage, ioException)
        } catch (illegalArgumentException: IllegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = getString(R.string.invalid_lat_long_used)
            Log.e(TAG, "$errorMessage. Latitude = $location.latitude , " +
                    "Longitude =  $location.longitude", illegalArgumentException)
        }

        // Handle case where no address was found.
        if (addresses.isEmpty()) {
            if (errorMessage.isEmpty()) {
                errorMessage = getString(R.string.no_address_found)
                Log.e(TAG, errorMessage)
            }
            deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage,"")
        } else {
            val address = addresses[0]
            // Fetch the address lines using getAddressLine,
            // join them, and send them to the thread.
            val cityName = address.locality
            val addressFragments = with(address) {
                (0..maxAddressLineIndex).map { getAddressLine(it) }
            }
            Log.i(TAG, getString(R.string.address_found))
            if(!cityName.isNullOrBlank() && !cityName.contains(getString(R.string.cityName),true)){
                deliverResultToReceiver(Constants.FAILURE_RESULT,getString(R.string.area_not_covered),cityName)
            }else{
                deliverResultToReceiver(Constants.SUCCESS_RESULT,
                    addressFragments.joinToString(separator = "\n"),cityName)
            }
        }
    }

    /**
     * Return the address to the requester
     */
    private fun deliverResultToReceiver(resultCode: Int, message: String, city: String?) {
        val bundle = Bundle().apply { putString(Constants.RESULT_DATA_KEY, message)
            putString(Constants.CITY_KEY, city)
        }
        receiver?.send(resultCode, bundle)
    }

}
