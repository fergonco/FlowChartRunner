package org.fergonco.flowchartrunner;

public class Answer {

	private String answer;
	private char key;
	private Step step;
	
	public Answer(String answer, char key, Step step) {
		this.answer = answer;
		this.key = key;
		this.step = step;
	}

	public String getText() {
		return answer;
	}

	public char getKey() {
		return key;
	}

	public Step getStep() {
		return step;
	}

}
