package zk.example.order.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

import zk.example.i18n.NlsFunctions;
import zk.example.order.api.validation.PaymentGroup;

public class Payment {
	private PaymentMethodType method;
	private CreditCard creditCard;
	private BankAccount bankAccount;

	@NotNull(groups={PaymentGroup.class, Default.class}, message="{payment.method.empty}")
	public PaymentMethodType getMethod() {
		return method;
	}
	public void setMethod(PaymentMethodType method) {
		this.method = method;
	}

	@Valid
	public CreditCard getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@Valid
	public BankAccount getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	public String toString() {
		PaymentMethodType methodType = getMethod();
		if(methodType != null) {
			String method = NlsFunctions.nls(methodType);
			switch(getMethod()) {
				case CASH_ON_DELIVERY:
				case PAYPAL:
					return NlsFunctions.nlsArgs("order.payment.format", method);
				case CREDIT_CARD:
					return NlsFunctions.nlsArgs("order.payment.format.alt", method, getCreditCard());
				case DIRECT_DEBIT:
					return NlsFunctions.nlsArgs("order.payment.format.alt", method, getBankAccount());
			}
		}
		return NlsFunctions.nls("order.payment.noMethod");
	}
}
