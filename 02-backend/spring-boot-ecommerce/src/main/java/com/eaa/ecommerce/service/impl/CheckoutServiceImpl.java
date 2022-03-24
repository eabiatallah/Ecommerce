package com.eaa.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eaa.ecommerce.dto.PaymentInfo;
import com.eaa.ecommerce.dto.PurchaseRequest;
import com.eaa.ecommerce.dto.PurchaseResponse;
import com.eaa.ecommerce.model.Address;
import com.eaa.ecommerce.model.Customer;
import com.eaa.ecommerce.model.Order;
import com.eaa.ecommerce.model.OrderItem;
import com.eaa.ecommerce.repository.AddressRepository;
import com.eaa.ecommerce.repository.CustomerRepository;
import com.eaa.ecommerce.repository.OrderItemRepository;
import com.eaa.ecommerce.repository.OrderRepository;
import com.eaa.ecommerce.service.CheckoutService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

@Service
public class CheckoutServiceImpl implements CheckoutService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private OrderRepository orderRepository; 
	
	@Autowired
	private AddressRepository AddressRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
//	@Value("${stripe.key.secret}") 
//	private String secretKey;
	
	
	public CheckoutServiceImpl(@Value("${stripe.key.secret}") String secretKey) {
		// initialize Stripe API with secret key
        Stripe.apiKey = secretKey;
	}

	@Override
//	@Transactional
	public PurchaseResponse placeOrder(PurchaseRequest purchase) {
		
		Customer customer = populateCustomer(purchase.getCustomer());
		Address shippingAddress = populateShippingAddress(purchase.getShippingAddress());
		Address billingAddress = populateBillingAddress(purchase.getBillingAddress());
		Order order = populateOrder(purchase.getOrder(), customer, shippingAddress, billingAddress);
		Set<OrderItem> orderItems = purchase.getOrderItems();
		if (orderItems != null) {
			orderItems.forEach(orderItem -> {
				OrderItem orderItm = populateOrderItems(orderItem, order);
				orderItemRepository.save(orderItm);
			});
		}
		return new PurchaseResponse(order.getOrderTrackingNumber());

	}

	private OrderItem populateOrderItems(OrderItem orderItem, Order order) {
		orderItem.setOrder(order);		
		return orderItem;
	}
	
	@Override
	public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {
		List<String> paymentMethodTypes = new ArrayList<>();
		paymentMethodTypes.add("card");

		Map<String, Object> params = new HashMap<>();
		params.put("amount", paymentInfo.getAmount());
		params.put("currency", paymentInfo.getCurrency());
		params.put("payment_method_types", paymentMethodTypes);
		params.put("description", "Luv2Shop purchase");
		params.put("receipt_email", paymentInfo.getReceiptEmail());

		// communicate with stripe backend service
		return PaymentIntent.create(params);

	}

	 
	private Address populateBillingAddress(Address billingAddress) {
		return AddressRepository.save(billingAddress);
	}

	private Address populateShippingAddress(Address shippingAddress) {
		return AddressRepository.save(shippingAddress);
	}

	private Order populateOrder(Order order, Customer customer, Address shippingAddress, Address billingAddress) {
		order.setCustomer(customer);
		order.setShippingAddress(shippingAddress);
		order.setBillingAddress(billingAddress);
		String orderTrackingNumber = generateOrderTrackingNumber();
		order.setOrderTrackingNumber(orderTrackingNumber);
		return orderRepository.save(order);
	}

	private Customer populateCustomer(Customer customer) {
		return customerRepository.save(customer);
		
	}

	private String generateOrderTrackingNumber() {
		// generate a random UUID number (UUID version-4)
		// For details see:
		// https://en.wikipedia.org/wiki/Universally_unique_identifier
		//
		return UUID.randomUUID().toString();
	}

}
