package com.ahmednmahran.egoshopping.view

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ahmednmahran.egoshopping.BuildConfig
import com.ahmednmahran.egoshopping.R
import com.google.firebase.auth.FirebaseAuth
import com.firebase.ui.auth.AuthUI
import java.util.*
import java.util.Arrays.asList
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.widget.Toast
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity() {
    val RC_SIGN_IN = 532
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            // already signed in
        } else {
            // not signed in
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setIsSmartLockEnabled(!BuildConfig.DEBUG /* credentials */, true /* hints */)
                    .setAvailableProviders(
                        Arrays.asList(
                            AuthUI.IdpConfig.EmailBuilder().build(),
                            AuthUI.IdpConfig.PhoneBuilder().build()
                        )
                    ).setTosAndPrivacyPolicyUrls("https://google.com","https://facebook.com/kotlinegypt")
                    .build(),
                RC_SIGN_IN
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            // Successfully signed in
            if (resultCode == Activity.RESULT_OK) {
//                startActivity(createIntent(this, response))
                var intent = Intent(this@MainActivity, SignedInActivity::class.java)
                intent.putExtra("response",response)
                startActivity(intent)
                finish()
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    toast("Sign In Failed")

                    return
                }

                if (response.error!!.errorCode == ErrorCodes.NO_NETWORK) {
                    toast("no_internet_connection")
                    return
                }

                toast("unknown_error")
                Log.e("MainActivity", "Sign-in error: ", response.error)
            }
        }
    }
}
