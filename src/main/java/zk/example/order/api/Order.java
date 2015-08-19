package zk.example.order.api;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

import zk.example.order.api.validation.BasketGroup;
import zk.example.order.api.validation.PaymentGroup;
import zk.example.order.api.validation.ShippingGroup;

public class Order {

	private Basket basket;
	private ShippingAddress shippingAddress;
	private Payment payment;
	private boolean accepted;

	@Valid
	@NotNull(groups={BasketGroup.class, Default.class})
	public Basket getBasket() {
		return basket;
	}
	public void setBasket(Basket basket) {
		this.basket = basket;
	}
	
	@Valid
	@NotNull(groups={ShippingGroup.class, Default.class})
	public ShippingAddress getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(ShippingAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	@Valid
	@NotNull(groups={PaymentGroup.class, Default.class})
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	@AssertTrue(message = "{order.accept}")
	public boolean isAccepted() {
		return accepted;
	}
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	};
}
