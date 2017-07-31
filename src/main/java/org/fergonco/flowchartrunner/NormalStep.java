package org.fergonco.flowchartrunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class NormalStep implements Step {

	private String text;
	private ArrayList<Answer> answers = new ArrayList<>();
	private HashMap<Character, Answer> answerKeys = new HashMap<>();
	private Scanner scanner = new Scanner(System.in);

	public NormalStep(String text) {
		this.text = text;
	}

	public void addPointer(String text, char key, Step step) {
		Answer answer = new Answer(text, key, step);
		this.answers.add(answer);
		this.answerKeys.put(key, answer);
	}

	@Override
	public void run(FlowChart flowChart, Step nextStep) throws IOException, FlowChartException {
		System.out.println(text);
		for (Answer answer : answers) {
			System.out.println(answer.getText() + "(" + answer.getKey() + ")");
		}
		try {
			if (answers.size() == 0) {
				scanner.nextLine();
			} else {
				Answer answer = null;
				while (answer == null) {
					String ch = scanner.nextLine().trim();
					if (ch.length() > 0) {
						answer = answerKeys.get(ch.charAt(0));
						if (answer != null) {
							flowChart.jumpToStep(answer.getStep());
						}
					}
				}
			}
		} catch (NoSuchElementException e) {
			flowChart.exit();
		}
	}

}
