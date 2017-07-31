package org.fergonco.flowchartrunner;

import java.io.IOException;

public interface Step {

	void run(FlowChart flowchart, Step nextStep) throws IOException, FlowChartException;

}
