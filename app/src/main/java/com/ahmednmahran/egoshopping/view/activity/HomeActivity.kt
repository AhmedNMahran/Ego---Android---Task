package com.ahmednmahran.egoshopping.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.ahmednmahran.egoshopping.R
import com.ahmednmahran.egoshopping.controller.navigation.loadCheckout
import com.ahmednmahran.egoshopping.controller.navigation.loadMap
import com.ahmednmahran.egoshopping.controller.navigation.loadPayment
import com.ahmednmahran.egoshopping.controller.settings.AppPreference
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.alert
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.Task


class HomeActivity : AppCompatActivity() {

    public var cityName = ""
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                loadPayment(this)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_map -> {
                loadMap(this)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_checkout -> {
                val addressAllowed = cityName.contains(getString(R.string.cityName))
                if (addressAllowed)
                    loadCheckout(this)
                else{
                    alert(
                        title = getString(R.string.choose_address),
                        message = getString(R.string.area_not_covered)
                    ).show()
                }
                return@OnNavigationItemSelectedListener addressAllowed
            }
        }
        false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        if (AppPreference.getInstance().localeLanguage.contains(AppPreference.EN, true))
            menu.findItem(R.id.en).isChecked = true
        else
            menu.findItem(R.id.ar).isChecked = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ar -> {
                reopenActivity(AppPreference.AR)
            }
            R.id.en ->
                reopenActivity(AppPreference.EN)
            else -> {
                AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener {
                        // user is now signed out
                        startActivity(Intent(this@HomeActivity, MainActivity::class.java))
                        finish()
                    }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun reopenActivity(language: String) {
        AppPreference.getInstance().setLocale(language)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppPreference.getInstance().updateUIperLanguage(this)
        setContentView(R.layout.activity_home)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.findViewById<View>(R.id.navigation_map).performClick()

    }

}
