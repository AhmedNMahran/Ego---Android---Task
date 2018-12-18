package com.ahmednmahran.egoshopping.controller.payment;

import com.ahmednmahran.egoshopping.controller.settings.AppPreference;
import com.ahmednmahran.egoshopping.model.Product;
import com.stripe.android.model.Card;

/**
 * Created by Ahmed Nabil on 12/19/18.
 */
public class Payment {

    private final PaymentCallback callback;
private static Payment instance;
    private Payment(PaymentCallback callback){
        this.callback = callback;
    }

    public static Payment getInstance(PaymentCallback callback){
        if(instance== null)
            instance = new Payment(callback);
        return instance;
    }

    /**
     * this method should process the payment and reutrn the status through {@link PaymentCallback}
     * but for now it fakes the payment success.
     * @param card
     * @param product
     */
    public Payment purchase(Card card, Product product){
        callback.onPaymentSuccessful(product.getPrice()+AppPreference.getInstance().getUpSellingProduct().getPrice()+product.getDeliveryCost()+"");
        return this;
    }

    public interface PaymentCallback {
        void onPaymentSuccessful(String message);
        void onPaymentFailure();
    }
}
