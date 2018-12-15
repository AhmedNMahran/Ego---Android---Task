package com.ahmednmahran.egoshopping.controller.location;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by Ahmed Nabil on 12/16/18.
 */
public class AddressResultReceiver extends ResultReceiver {
//    internal inner class AddressResultReceiver(handler: Handler) : ResultReceiver(handler) {
//
//        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
//
//            // Display the address string
//            // or an error message sent from the intent service.
//            addressOutput = resultData?.getString(Constants.RESULT_DATA_KEY) ?: ""
//            updateUI(addressOutput)
//            // Show a toast message if an address was found.
//            if (resultCode == Constants.SUCCESS_RESULT) {
//                toast(getString(R.string.address_found))
//            }
//
//        }
//    }
private Receiver mReceiver;

    public AddressResultReceiver(Handler handler) {
        super(handler);
        // TODO Auto-generated constructor stub
    }

    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);

    }

    public void setReceiver(Receiver receiver) {
        mReceiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {

        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }

}
