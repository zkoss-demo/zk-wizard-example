package zk.example.survey;

import java.util.Arrays;
import java.util.List;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;

import zk.example.wizard.model.WizardStep;
import zk.example.wizard.model.WizardViewModel;

public class SurveyViewModel {
	private static final String STEP_FOLDER = "/WEB-INF/zul/survey/steps/";

	private WizardViewModel<WizardStep> wizardModel;
	private Survey survey;

	@Init
	public void init() {
		survey = new Survey();
		initWizardModel();
	}

	private void initWizardModel() {
		List<WizardStep> availableSteps = Arrays.asList(
				wizardStep("question_1"),
				wizardStep("question_2"),
				wizardStep("question_3"),
				wizardStep("done")
					.withBeforeNextHandler(() -> Executions.sendRedirect("./survey.zul"))
					.withNextLabel("Restart")
				);

		wizardModel = new WizardViewModel<WizardStep>(availableSteps);
	}

	private WizardStep wizardStep(String stepId) {
		String title = stepId;
		String templateUri = STEP_FOLDER + stepId + ".zul";
		return new WizardStep(stepId, title, templateUri);
	}

	public WizardViewModel<WizardStep> getWizardModel() {
		return wizardModel;
	}

	public Survey getSurvey() {
		return survey;
	}
}
