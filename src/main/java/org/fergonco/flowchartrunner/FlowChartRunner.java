package org.fergonco.flowchartrunner;

import java.io.IOException;

public class FlowChartRunner 
{
    public static void main( String[] args ) throws IOException, FlowChartException
    {
    	Parser parser = new Parser();
    	FlowChart flowChart = parser.parse(args[0]);
    	flowChart.run();
    }
}
