package com.ahmednmahran.egoshopping.model

import com.google.android.gms.maps.model.LatLng
import java.util.*

/**
 * Created by Ahmed Nabil on 12/14/18.
 */
/**
 * The user who uses the application to place orders
 */
data class User(var name: String, var age: Double, var addressDescription: String="", var geoLocation: LatLng)

/**
 * The item being selected by {@User}
 */
data class Product(var name: String, var price: Double, var store: Store, var deliveryCost: Double, var deliveryDate: Calendar)

/**
 * The Store from which the user buys the products
 */
data class Store(var name: String, var address: String, var addressEn: String, var geolocation: LatLng)