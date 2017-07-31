package org.fergonco.flowchartrunner;

import java.io.IOException;
import java.util.Scanner;

public class EndStep implements Step {

	private String message;
	private Scanner scanner = new Scanner(System.in);

	public EndStep(String message) {
		this.message = message;
	}

	@Override
	public void run(FlowChart flowchart, Step nextStep) throws IOException {
		if (message != null) {
			System.out.println(message);
			scanner.nextLine();
		}
		flowchart.end();
	}

}
