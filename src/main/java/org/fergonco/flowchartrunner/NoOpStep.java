package org.fergonco.flowchartrunner;

public class NoOpStep implements Step {

	@Override
	public void run(FlowChart flowChart, Step nextStep) {
	}

}
