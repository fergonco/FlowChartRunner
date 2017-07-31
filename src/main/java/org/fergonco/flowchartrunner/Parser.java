package org.fergonco.flowchartrunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

	private Pattern answerPattern = Pattern.compile("-\\((\\w)\\)(.+)");
	private Pattern answerStepPattern = Pattern.compile("^\\s+.+");

	public FlowChart parse(String path) throws IOException, FlowChartException {

		BufferedReader reader = new BufferedReader(new FileReader(path));
		try {
			String line;
			NormalStep lastStep = null;
			FlowChart flowChart = new FlowChart();
			while ((line = reader.readLine()) != null) {
				boolean indented = answerStepPattern.matcher(line).find();
				line = line.trim();
				Step command = getCommand(line);
				if (command != null) {
					flowChart.addStep(command);
				} else if (line.startsWith("-")) {
					Matcher matcher = answerPattern.matcher(line);
					if (matcher.find()) {
						String key = matcher.group(1);
						String text = matcher.group(2);
						Step step = new NoOpStep();
						flowChart.addStep(step);
						lastStep.addPointer(text, key.charAt(0), step);
					} else {
						throw new FlowChartException(
								"Line starts with - but does not follow correct syntax: -(<key-shortcut>)<answer-text>");
					}
				} else if (line.endsWith(":")) {
					String pointer = line.substring(0, line.length() - 1);
					Step step = new NoOpStep();
					flowChart.addStep(step);
					flowChart.addPointer(pointer, step);
				} else {
					NormalStep step = new NormalStep(line);
					flowChart.addStep(step);
					if (!indented) {
						lastStep = step;
					}
				}
			}
			return flowChart;
		} finally {
			reader.close();
		}
	}

	private Step getCommand(String line) {
		Step ret = null;
		if (line.startsWith("GOTO ")) {
			ret = new GotoStep(line.substring("GOTO ".length()));
		} else if (line.startsWith("RUN ")) {
			ret = new RunStep(line.substring("RUN ".length()));
		} else if (line.startsWith("NOOP")) {
			ret = new NoOpStep();
		} else if (line.startsWith("END")) {
			String message = null;
			if (line.length() > 3 && line.charAt(3) == '(') {
				message = line.substring("END(".length(), line.length() - 1);
			}

			ret = new EndStep(message);
		}
		return ret;
	}

}
