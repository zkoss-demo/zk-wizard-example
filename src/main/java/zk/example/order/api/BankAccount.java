package zk.example.order.api;

import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

import zk.example.i18n.NlsFunctions;
import zk.example.order.api.validation.PaymentGroup;

public class BankAccount {
	private String iban;
	private String bic;

	@NotNull(groups={PaymentGroup.class, Default.class})
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}

	@NotNull(groups={PaymentGroup.class, Default.class})
	public String getBic() {
		return bic;
	}
	public void setBic(String bic) {
		this.bic = bic;
	}
	
	@Override
	public String toString() {
		return NlsFunctions.nlsArgs("order.payment.bankAccount.format", getIban(), getBic());
	}
}
