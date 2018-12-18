package com.ahmednmahran.egoshopping.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ahmednmahran.egoshopping.R
import com.ahmednmahran.egoshopping.controller.notification.NotificationManager
import com.ahmednmahran.egoshopping.controller.adapter.OnAppSelectedListener
import com.ahmednmahran.egoshopping.controller.adapter.UpSellingAdapter
import com.ahmednmahran.egoshopping.controller.navigation.loadPayment
import com.ahmednmahran.egoshopping.controller.networking.ApiFacade
import com.ahmednmahran.egoshopping.controller.networking.Callback
import com.ahmednmahran.egoshopping.controller.payment.Payment
import com.ahmednmahran.egoshopping.controller.settings.AppPreference
import com.ahmednmahran.egoshopping.model.App
import kotlinx.android.synthetic.main.fragment_checkout.*
import kotlinx.android.synthetic.main.fragment_checkout.view.*
import org.jetbrains.anko.okButton
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.toast
import java.text.SimpleDateFormat

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * Use the [CheckoutFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CheckoutFragment : Fragment(), Callback.NetworkCallback<List<App>>, OnAppSelectedListener,
    Payment.PaymentCallback {
    override fun onPaymentSuccessful(message: String) {
        NotificationManager.sendNotification(context!!,getString(R.string.payment_successful),getString(R.string.price)+": "+message+" "+getString(R.string.riyal))
    }

    override fun onPaymentFailure() {

    }

    private lateinit var appPreference: AppPreference

    override fun onItemSelected(app: App?) {
        AppPreference.getInstance().saveSelectedUpSelling(app)
        updateOrder()
    }

    override fun onSuccess(returnedData: List<App>?) {
        rvApps.adapter = UpSellingAdapter(returnedData, context!!, this)
    }

    override fun onFailure(message: String?) {
        toast("$message")
    }

    private lateinit var simpleDateFormat: SimpleDateFormat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        simpleDateFormat = SimpleDateFormat("DD-MM-YY HH:MM a")
        appPreference = AppPreference.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_checkout, container, false)
        ApiFacade.getInstance().getUpSellingData(this)
        view.rvApps.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateOrder()
        view.btnCheckout.onClick {
            val card = appPreference.card
            if (card != null) {
                Payment.getInstance(this@CheckoutFragment).purchase(card, appPreference.savedProduct)
            } else {
                val alert =
                    alert(getString(R.string.add_payment_method_message), getString(R.string.no_payment_selected))
                alert.okButton { loadPayment(activity as AppCompatActivity) }
                alert.show()
            }
        }
    }

    private fun updateOrder() {
        val savedUser = appPreference.savedUser
        val savedProduct = appPreference.savedProduct
        val upsellingProduct = appPreference.upSellingProduct
        var extra = 0.0
        if (upsellingProduct != null)
            extra = upsellingProduct.price
        val addressNotes = savedUser.addressNotes
        val orderDetails = getString(
            R.string.order_summery, """${getString(R.string.name)}: ${savedUser.name}
            ${getString(R.string.age)}: ${savedUser.age}
            ${getString(R.string.address)}: ${savedUser.addressDescription}
            ${if (!addressNotes.isNullOrEmpty()) addressNotes else {
                ""
            }}
        """, """${getString(R.string.name)}: ${savedProduct.name}
            ${getString(R.string.price)}: ${savedProduct.price}
            ${getString(R.string.delivery_fees)}: ${savedProduct.deliveryCost}
            ${if (upsellingProduct != null) {
                getString(
                    R.string.upselling, """
                        ${getString(R.string.name)}: ${upsellingProduct.name}
            ${getString(R.string.price)}: ${upsellingProduct.price}"""
                )
            } else {
                ""
            }
            }
            ${getString(R.string.delivery_date)}: ${simpleDateFormat.format(savedProduct.deliveryDate)}
        """, "${savedProduct.price + savedProduct.deliveryCost + extra} "
        )
        if (savedProduct.deliveryCost > 0)
            tvFree.visibility = View.GONE
        else
            tvFree.visibility = View.VISIBLE
        tvOrderSummery.text = orderDetails
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment
         * @return A new instance of fragment CheckoutFragmentFragment.
         */
        @JvmStatic
        fun newInstance() = CheckoutFragment()
    }
}
