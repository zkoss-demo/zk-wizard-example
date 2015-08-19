package zk.example.order;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zul.ListModelList;

import zk.example.i18n.NlsFunctions;
import zk.example.order.api.Basket;
import zk.example.order.api.BasketItem;
import zk.example.order.service.RecommendationService;

public class BasketViewModel {

	//in the real life application injected using @WireVariable
	private RecommendationService recommendationService = new RecommendationService();
	
	private Basket basket;
	private ListModelList<BasketItem> basketItemsModel;
	private ListModelList<BasketItem> recommendedItemsModel;

	private DecimalFormat priceFormat = new DecimalFormat(NlsFunctions.nls("order.price.format"));
	
	@Init 
	public void init(@BindingParam("basket") Basket basket) {
		this.basket = basket;
		//live list model to reflect changes directly in basket.items collection
		basketItemsModel = new ListModelList<>(this.basket.getItems(), true); //true -> live list
		recommendedItemsModel = new ListModelList<>();
		loadRecommendations();
	}

	@Command("removeItem")
	public void removeItem(@BindingParam("basketItem") BasketItem basketItem) {
		basketItemsModel.remove(basketItem);
		BindUtils.postNotifyChange(null, null, basket, "totalPrice");
		loadRecommendations();
	}
	
	@Command("addRecommendedItem")
	public void addRecommendedItem(@BindingParam("item") BasketItem item) {
		basketItemsModel.add(item);
		BindUtils.postNotifyChange(null, null, basket, "totalPrice");
		loadRecommendations();
	}
	
	@Command("updateQuantity")
	public void updateQuantity(@BindingParam("basketItem") BasketItem basketItem) {
		BindUtils.postNotifyChange(null, null, basketItem, "itemPrice");
		BindUtils.postNotifyChange(null, null, basket, "totalPrice");
	}

	private void loadRecommendations() {
		recommendedItemsModel.clear();
		recommendedItemsModel.addAll(recommendationService.chooseRecommendations(this.basket));
		BindUtils.postNotifyChange(null, null, this, "hasRecommendations");
	}
	
	public ListModelList<BasketItem> getItemsModel() {
		return basketItemsModel;
	}
	
	public ListModelList<BasketItem> getRecommendedItemsModel() {
		return recommendedItemsModel;
	}
	
	public boolean isHasRecommendations() {
		return !recommendedItemsModel.isEmpty();
	}
	
	public ReadOnlyConverter<BigDecimal, String> getPriceFormatter() {
		return priceFormat::format;
	}
	
	public ReadOnlyConverter<BigDecimal, String> getPriceFormatterParentheses() {
		return price -> "(" + priceFormat.format(price) + ")";
	}
}
