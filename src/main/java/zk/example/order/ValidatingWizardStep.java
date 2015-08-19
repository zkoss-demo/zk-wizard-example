package zk.example.order;

import zk.example.wizard.model.WizardStep;

public class ValidatingWizardStep extends WizardStep {

	private Class<?>[] validationGroups;

	public ValidatingWizardStep(String id, String title, String templateUri, Class<?>[] validationGroups) {
		super(id, title, templateUri);
		this.validationGroups = validationGroups;
	}

	public Class<?>[] getValidationGroups() {
		return validationGroups;
	}
}
