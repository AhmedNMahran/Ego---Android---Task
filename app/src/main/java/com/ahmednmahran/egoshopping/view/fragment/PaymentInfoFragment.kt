package com.ahmednmahran.egoshopping.view.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ahmednmahran.egoshopping.R
import com.ahmednmahran.egoshopping.controller.settings.AppPreference
import kotlinx.android.synthetic.main.fragment_payment_info.*
import kotlinx.android.synthetic.main.fragment_payment_info.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PaymentInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class PaymentInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_payment_info, container, false)
        val appPreference = AppPreference.getInstance()
        val card = appPreference.card
        val user= appPreference.savedUser
        view.tvUserInfo.text = """
            ${getString(R.string.name)}: ${user.name}
            ${getString(R.string.age)}: ${user.age}

        """.trimIndent()
        if(card != null){
            view.card_input_widget.setCardNumber(card.number)
            view.card_input_widget.setCvcCode(card.cvc)
            view.card_input_widget.setExpiryDate(card.expMonth!!,card.expYear)
            view.btnSaveCard.text = getString(R.string.update_card)
        }
        view.btnSaveCard.onClick {
            val cardToSave = view.card_input_widget.card
            if (cardToSave == null) {
                toast("Invalid Card Data")
            }
            else if(cardToSave.validateCard()){
                appPreference.saveCard(cardToSave)
            }
        }
        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment
         * @return A new instance of fragment PaymentInfoFragment.
         */
        @JvmStatic
        fun newInstance() = PaymentInfoFragment()
    }
}
