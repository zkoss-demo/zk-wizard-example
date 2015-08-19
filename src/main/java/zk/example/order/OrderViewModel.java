package zk.example.order;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.validation.groups.Default;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;

import zk.example.i18n.NlsFunctions;
import zk.example.order.api.Basket;
import zk.example.order.api.BasketItem;
import zk.example.order.api.BasketItemStatusType;
import zk.example.order.api.Order;
import zk.example.order.api.Payment;
import zk.example.order.api.ShippingAddress;
import zk.example.order.api.validation.BasketGroup;
import zk.example.order.api.validation.PaymentGroup;
import zk.example.order.api.validation.ShippingGroup;
import zk.example.order.service.OrderService;
import zk.example.wizard.Bookmark;
import zk.example.wizard.model.WizardStep;
import zk.example.wizard.model.WizardViewModel;

public class OrderViewModel {
	private static final String STEP_FOLDER = "/WEB-INF/zul/order/steps/";
	private static final String BASKET = "basket";
	private static final String SHIPPING_ADDRESS = "shippingAddress";
	private static final String PAYMENT = "payment";
	private static final String CONFIRMATION = "confirmation";
	private static final String FEEDBACK = "feedback";

	//in the real life application injected using @WireVariable
	private OrderService orderService = new OrderService();
	private Bookmark bookmark = new Bookmark();
	
	private Order order;
	private WizardViewModel<WizardStep> wizardModel;

	@Init
	public void init() {
		//fill basket with some data
		Basket basket = new Basket();
		basket.getItems().add(new BasketItem("Head Phones", 1, new BigDecimal("59.99"), BasketItemStatusType.AVAILABLE));
		basket.getItems().add(new BasketItem("Screen Wipes", 1, new BigDecimal("4.49"), BasketItemStatusType.AVAILABLE));
		basket.getItems().add(new BasketItem("Beer Six-pack", 4, new BigDecimal("3.29"), BasketItemStatusType.LOW_STOCK));
		basket.getItems().add(new BasketItem("Paper clips", 20, new BigDecimal("0.09"), BasketItemStatusType.SOLD_OUT));

		order = new Order();
		order.setBasket(basket);
		order.setPayment(new Payment());
		order.setShippingAddress(new ShippingAddress());

		initWizardModel();
	}

	private void initWizardModel() {
		List<WizardStep> availableSteps = Arrays.asList(
				wizardStep(BASKET, BasketGroup.class),
				wizardStep(SHIPPING_ADDRESS, ShippingGroup.class),
				wizardStep(PAYMENT, PaymentGroup.class),
				wizardStep(CONFIRMATION, Default.class)
					.withBeforeNextHandler(this::sendOrder)
					.withNextLabel(NlsFunctions.nls("order.confirmation.button.sendNow")),
				wizardStep(FEEDBACK)
					.withBeforeNextHandler(this::newOrder)
					.withNextLabel(NlsFunctions.nls("order.feedback.button.newOrder"))
				);

		wizardModel = new WizardViewModel<WizardStep>(availableSteps) {
			@Override
			protected void onStepChanged(WizardStep currentStep) {
				bookmark.set(currentStep.getId(), false);
			}
		};
	}

	private ValidatingWizardStep wizardStep(String stepId, Class<?>... validationGroups) {
		String title = NlsFunctions.nls("order." + stepId + ".title");
		String templateUri = STEP_FOLDER + stepId + ".zul";
		return new ValidatingWizardStep(stepId, title, templateUri, validationGroups);
	}

	private void sendOrder() {
		orderService.send(order); 
	}

	private void newOrder() {
		Executions.sendRedirect("order.zul");		
	}

	@Command("gotoStep")
	public void gotoStep(@BindingParam("stepId") String stepId) {
		if(!getWizardModel().gotoStep(stepId)) {
			//if step change unsuccessful override the bookmark
			bookmark.set(wizardModel.getCurrentStep().getId(), true);
		};
	}
	
	public WizardViewModel<WizardStep> getWizardModel() {
		return wizardModel;
	}

	public Order getOrder() {
		return order;
	}
}
