package zk.example.order.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import zk.example.order.api.Basket;
import zk.example.order.api.BasketItem;
import zk.example.order.api.BasketItemStatusType;

public class RecommendationService {
	public List<BasketItem> chooseRecommendations(Basket basket) {
		List<BasketItem> result = new ArrayList<BasketItem>();
		//just some dummy recommendations your company will have more sophisticated rules engine
		testRecommendation(basket, "Beer", "German Sausages", "2.95").ifPresent(result::add);
		testRecommendation(basket, "German Sausages", "Delicious Bavarian Mustard", "1.50").ifPresent(result::add);
		testRecommendation(basket, "Head Phones", "MP3 Player", "179.00").ifPresent(result::add);
		testRecommendation(basket, "Paper clips", "More Paper Clips", "0.07").ifPresent(result::add);
		return result;
	}

	private Optional<BasketItem> testRecommendation(Basket basket, String existing, String recommendation, String price) {
		if(matchesKeyword(basket, existing) && notContains(basket, recommendation)) {
			return Optional.of(new BasketItem(recommendation, 1, new BigDecimal(price), BasketItemStatusType.AVAILABLE));
		}
		return Optional.empty();
	}

	private boolean notContains(Basket basket, String keyword) {
		return basket.getItems().stream().noneMatch(item -> item.getLabel().contains(keyword));
	}

	private boolean matchesKeyword(Basket basket, String keyword) {
		return basket.getItems().stream().anyMatch(item -> item.getLabel().contains(keyword));
	}
}
