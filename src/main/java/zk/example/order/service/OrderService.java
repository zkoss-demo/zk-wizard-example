package zk.example.order.service;

import zk.example.order.api.Order;
import zk.example.order.api.OrderException;
import zk.example.order.api.PaymentMethodType;

public class OrderService {
	public void send(Order order) {
		System.out.println("Trying to send Order");
		if(order.getPayment().getMethod() == PaymentMethodType.PAYPAL) {
			throw new OrderException("Paypal is currently not available. Please choose a different Payment method.");
		}
		System.out.println("Send Order successful");
	}
}
