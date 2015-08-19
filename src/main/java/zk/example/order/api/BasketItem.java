package zk.example.order.api;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

import org.zkoss.bind.annotation.Immutable;
import org.zkoss.bind.annotation.Transient;

import zk.example.order.api.validation.BasketGroup;


public class BasketItem {
	private String label;
	private int quantity;
	private BigDecimal unitPrice;
	private BasketItemStatusType status;

	public BasketItem() {}

	public BasketItem(String label, int quantity, BigDecimal unitPrice, BasketItemStatusType status) {
		this.label = label;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.status = status;
	}

	@Transient
	public BigDecimal getItemPrice() {
		return getUnitPrice().multiply(BigDecimal.valueOf(getQuantity()));
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	@Immutable
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	@Min(value = 1, groups={BasketGroup.class, Default.class})
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@NotNull(groups={BasketGroup.class, Default.class})
	public BasketItemStatusType getStatus() {
		return status;
	}
	public void setStatus(BasketItemStatusType status) {
		this.status = status;
	}
}
