package org.fergonco.flowchartrunner;

import java.io.IOException;

public class RunStep implements Step {

	private String targetStep;

	public RunStep(String targetStep) {
		this.targetStep = targetStep;
	}

	@Override
	public void run(FlowChart flowChart, Step nextStep) throws IOException, FlowChartException {
		flowChart.runLabel(targetStep, nextStep);
	}

}
