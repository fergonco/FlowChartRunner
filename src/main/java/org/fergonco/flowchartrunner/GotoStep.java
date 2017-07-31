package org.fergonco.flowchartrunner;

import java.io.IOException;

public class GotoStep implements Step {

	private String targetStep;

	public GotoStep(String targetStep) {
		this.targetStep = targetStep;
	}

	@Override
	public void run(FlowChart flowChart, Step nextStep) throws IOException, FlowChartException {
		flowChart.jumpToLabel(targetStep);
	}

}
