package zk.example.order.api;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.zkoss.bind.annotation.Transient;

import zk.example.i18n.NlsFunctions;
import zk.example.order.api.validation.BasketGroup;

public class Basket {
	private List<BasketItem> items = new ArrayList<BasketItem>();
	
	@Valid
	@NotNull(groups={BasketGroup.class, Default.class})
	@Size(min=1, groups={BasketGroup.class, Default.class}, message="{basket.empty}")
	public List<BasketItem> getItems() {
		return items;
	}

	@Transient
	public BigDecimal getTotalPrice() {
		return this.getItems().stream()
				.map(BasketItem::getItemPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@Transient
	public int getTotalItems() {
		return this.getItems().stream()
				.mapToInt(BasketItem::getQuantity)
				.sum();
	}

	@Override
	public String toString() {
		DecimalFormat decimalFormat = new DecimalFormat(NlsFunctions.nls("order.price.format"));
		return NlsFunctions.nlsArgs("order.basket.format", getTotalItems(), decimalFormat.format(getTotalPrice()));
	}
}
