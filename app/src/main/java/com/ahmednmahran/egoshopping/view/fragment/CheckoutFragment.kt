package com.ahmednmahran.egoshopping.view.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ahmednmahran.egoshopping.R
import com.ahmednmahran.egoshopping.controller.adapter.UpSellingAdapter
import com.ahmednmahran.egoshopping.controller.networking.ApiFacade
import com.ahmednmahran.egoshopping.controller.networking.Callback
import com.ahmednmahran.egoshopping.controller.settings.AppPreference
import com.ahmednmahran.egoshopping.model.App
import kotlinx.android.synthetic.main.fragment_checkout.*
import kotlinx.android.synthetic.main.fragment_checkout.view.*
import org.jetbrains.anko.support.v4.toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CheckoutFragmentFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CheckoutFragmentFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CheckoutFragment : Fragment(), Callback.NetworkCallback<List<App>> {
    override fun onSuccess(returnedData: List<App>?) {
        toast("${returnedData?.get(0)?.imageName}")
        rvApps.adapter = UpSellingAdapter(returnedData,context!!)
    }

    override fun onFailure(message: String?) {
        toast("$message")
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

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
        val view = inflater.inflate(R.layout.fragment_checkout, container, false)
        ApiFacade.getInstance().getUpSellingData(this)
        view.rvApps.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appPreference = AppPreference.getInstance()
        tvOrderSummery.text = """"${appPreference.savedUser}\n
            \n${appPreference.savedProduct}
            \n${getString(R.string.delivery_fees)}
            \n
        """.trimMargin()
    }
    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        listener = null
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CheckoutFragmentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CheckoutFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
