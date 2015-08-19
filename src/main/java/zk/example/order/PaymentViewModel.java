package zk.example.order;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;

import zk.example.order.api.BankAccount;
import zk.example.order.api.CreditCard;
import zk.example.order.api.CreditCardType;
import zk.example.order.api.Payment;
import zk.example.order.api.PaymentMethodType;

public class PaymentViewModel {
	private static final String PAYMENT_METHOD_UPDATE_COMMAND = "PaymentMethodUpdateCommand";
	private Payment payment;

	@Init 
	public void init(@BindingParam("payment") Payment payment) {
		this.payment = payment;
	}

	@Command(PAYMENT_METHOD_UPDATE_COMMAND)
	public void paymentMethodUpdated() {
		switch (payment.getMethod()) {
		case CREDIT_CARD:
			payment.setCreditCard(new CreditCard());
			payment.setBankAccount(null);
			break;
		case DIRECT_DEBIT:
			payment.setBankAccount(new BankAccount());
			payment.setCreditCard(null);
			break;
		default:
			payment.setBankAccount(null);
			payment.setCreditCard(null);
			break;
		}
		
		BindUtils.postNotifyChange(null, null, payment, "creditCard");
		BindUtils.postNotifyChange(null, null, payment, "bankAccount");
		BindUtils.postNotifyChange(null, null, this, "hasCreditCard");
		BindUtils.postNotifyChange(null, null, this, "hasBankAccount");
	}
	
	public PaymentMethodType[] getAvailablePaymentMethods() {
		return PaymentMethodType.values();
	}

	public CreditCardType[] getAvailableCreditCards() {
		return CreditCardType.values();
	}
	
	public String getPaymentMethodUpdateCommand() {
		return PAYMENT_METHOD_UPDATE_COMMAND;
	} 
	
	public boolean isHasCreditCard() {
		return payment.getCreditCard() != null;
	}

	public boolean isHasBankAccount() {
		return payment.getBankAccount() != null;
	}

}
