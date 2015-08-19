package zk.example.order.api;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import zk.example.i18n.NlsFunctions;
import zk.example.order.api.validation.PaymentGroup;

public class CreditCard {
	private CreditCardType type;
	private String number;
	private String owner;
	
	@NotNull(groups={PaymentGroup.class, Default.class}, message="{creditCard.type.empty}")
	public CreditCardType getType() {
		return type;
	}
	public void setType(CreditCardType type) {
		this.type = type;
	}

	@NotNull(groups={PaymentGroup.class, Default.class}, message="{field.empty}")
	@Size(min=16, groups={PaymentGroup.class, Default.class}, message="{creditCard.number.size}")
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}

	@NotNull(groups={PaymentGroup.class, Default.class}, message="{field.empty}")
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String toString() {
		String type = getType() != null ? NlsFunctions.nls(getType()) : NlsFunctions.nls("order.payment.creditCard.noType");
		String number = getNumber();
		String numberDisplay = number == null ? null : "xxxx" + number.substring(Math.max(number.length() - 4, 0));
		return NlsFunctions.nlsArgs("order.payment.creditCard.format", type, numberDisplay, getOwner());
	}
}
