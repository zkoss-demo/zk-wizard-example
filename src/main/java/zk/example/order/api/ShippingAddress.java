package zk.example.order.api;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import zk.example.i18n.NlsFunctions;
import zk.example.order.api.validation.ShippingGroup;

public class ShippingAddress {
	private String street;
	private String city;
	private String zipCode;

	@NotNull(groups={ShippingGroup.class, Default.class}, message="{field.empty}")
	@Size(min=4, max=50, groups={ShippingGroup.class, Default.class}, message="{field.size}")
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}

	@NotNull(groups={ShippingGroup.class, Default.class}, message="{field.empty}")
	@Size(min=1, max=30, groups={ShippingGroup.class, Default.class}, message="{field.size}")
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	@NotNull(groups={ShippingGroup.class, Default.class}, message="{field.empty}")
	@Size(min=1, max=10, groups={ShippingGroup.class, Default.class}, message="{field.size}")
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	@Override
	public String toString() {
		return NlsFunctions.nlsArgs("order.shippingAddress.format", getStreet(), getCity(), getZipCode());
	}
}
