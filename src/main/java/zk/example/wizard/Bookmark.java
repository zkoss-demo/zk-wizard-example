package zk.example.wizard;

import org.zkoss.zk.ui.Executions;

public class Bookmark {
	public void set(String currentStepId, boolean replaceBookmark) {
		Executions.getCurrent().getDesktop().setBookmark(currentStepId, replaceBookmark);
	}
}
