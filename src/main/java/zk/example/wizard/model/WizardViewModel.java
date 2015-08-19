package zk.example.wizard.model;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.DependsOn;

public class WizardViewModel<T extends WizardStep> {
	private static final String BACK_COMMAND = "back";
	private static final String NEXT_COMMAND = "next";

	private T currentStep;
	private List<T> availableSteps;
	private Map<String, T> stepLookup;

	public WizardViewModel(List<T> availableSteps) {
		this.availableSteps = availableSteps;
		this.currentStep = availableSteps.get(0);
		this.stepLookup = availableSteps.stream().collect(
				Collectors.toMap(WizardStep::getId, Function.identity()));
	}

	@Command(BACK_COMMAND) 
	public void back() {
		gotoStep(currentStepIndex() - 1);
	}
	
	@Command(NEXT_COMMAND)
	public void next() {
		getCurrentStep().onBeforeNext();
		if(!isLastStep()) {
			gotoStep(currentStepIndex() + 1);
		}
	}

	//callback hook to intercept different steps
	protected void onStepChanged(T currentStep) {}

	public boolean gotoStep(String stepId) {
		T step = stepLookup.get(stepId);
		if(step == null) {
			gotoStep(0);
			return false;
		}
		int index = availableSteps.indexOf(step);
		if(index >= currentStepIndex()) {
			//can only jump backwards
			return false;
		}
		gotoStep(index);
		return true; 
	}

	private void gotoStep(int index) {
		if (index < 0 || index >= availableSteps.size()) {
			throw new IllegalArgumentException("illegal step: " + index);
		} 
		currentStep = availableSteps.get(index);
		onStepChanged(getCurrentStep());
		BindUtils.postNotifyChange(null, null, this, "currentStep");
	}

	public T getCurrentStep() {
		return currentStep;
	}
	
	@DependsOn("currentStep")
	public String getCurrentStepTemplateUri() {
		return getCurrentStep().getTemplateUri();
	}
	
	@DependsOn("currentStep")
	public boolean isFirstStep() {
		return currentStep == availableSteps.get(0);
	}
	
	@DependsOn("currentStep")
	public boolean isLastStep() {
		return currentStep == availableSteps.get(availableSteps.size() - 1);
	}

	@DependsOn("currentStep")
	public int getProgress() {
		return 100 * currentStepIndex() / (availableSteps.size() - 1);
	}
	
	@DependsOn("currentStep")
	public boolean isBackVisible() {
		return !isFirstStep() && !isLastStep();
	}

	@DependsOn("currentStep")
	public boolean isNextVisible() {
		return true;
	}
	
	@DependsOn("currentStep")
	public String getBackLabel() {
		return currentStep.getBackLabel();
	}

	@DependsOn("currentStep")
	public String getNextLabel() {
		return currentStep.getNextLabel();
	}

	public String getBackCommand() {
		return BACK_COMMAND;
	}

	public String getNextCommand() {
		return NEXT_COMMAND;
	}

	public List<T> getPreviousSteps() {
		return availableSteps.subList(0, availableSteps.indexOf(getCurrentStep()));
	}

	private int currentStepIndex() {
		return availableSteps.indexOf(getCurrentStep());
	}
}

