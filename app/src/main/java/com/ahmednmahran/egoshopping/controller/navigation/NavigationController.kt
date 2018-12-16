package com.ahmednmahran.egoshopping.controller.navigation

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.ahmednmahran.egoshopping.R
import com.ahmednmahran.egoshopping.view.fragment.CheckoutFragment
import com.ahmednmahran.egoshopping.view.fragment.MapFragment

/**
 * Created by Ahmed Nabil on 12/17/18.
 */
fun loadMap(activity: AppCompatActivity) {
    val mapFragment = MapFragment.newInstance("", "")
    val tag = "MapFragment"
    replaceFragment(activity, mapFragment,tag)
}

fun loadCheckout(activity: AppCompatActivity) {
    val checkoutFragment = CheckoutFragment.newInstance("", "")
    val tag = "CheckoutFragment"
    replaceFragment(activity, checkoutFragment,tag)
}

private fun replaceFragment(
    activity: AppCompatActivity,
    fragment: Fragment,
    tag: String) {
    activity.supportFragmentManager.beginTransaction().replace(R.id.homeContainer, fragment, tag).commit()
}