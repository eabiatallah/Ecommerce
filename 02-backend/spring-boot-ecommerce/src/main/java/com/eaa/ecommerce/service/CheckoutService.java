package com.eaa.ecommerce.service;

import com.eaa.ecommerce.dto.PaymentInfo;
import com.eaa.ecommerce.dto.PurchaseRequest;
import com.eaa.ecommerce.dto.PurchaseResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface CheckoutService {
	
	PurchaseResponse placeOrder(PurchaseRequest purchase);
    PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;



}
