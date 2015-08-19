package zk.example.wizard.model;

import zk.example.i18n.NlsFunctions;

public class WizardStep {

	private String id;
	private String title;
	private String templateUri;
	private String nextLabel;
	private Runnable beforeNext = () -> {};
	
	public WizardStep(String id, String title, String templateUri) {
		this.id = id;
		this.title = title;
		this.templateUri = templateUri;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getTemplateUri() {
		return templateUri;
	}

	public String getNextLabel() {
		return nextLabel != null ? nextLabel : NlsFunctions.nls("wizard.button.next");
	}
	
	public String getBackLabel() {
		return NlsFunctions.nls("wizard.button.back");
	}
	
	public WizardStep withBeforeNextHandler(Runnable beforeNextHandler) {
		beforeNext = beforeNextHandler;
		return this;
	}

	public WizardStep withNextLabel(String nextLabel) {
		this.nextLabel = nextLabel;
		return this;
	}
	
	public void onBeforeNext() {
		beforeNext.run();
	}
}