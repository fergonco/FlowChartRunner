package org.fergonco.flowchartrunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Stack;

public class FlowChart {

	private ArrayList<Step> steps = new ArrayList<>();
	private HashMap<String, Step> pointers = new HashMap<>();
	private Step currentStep;
	private Stack<Step> executionStack = new Stack<>();
	private boolean alreadyInNext;
	private boolean exit = false;

	public void addStep(Step step) {
		this.steps.add(step);
	}

	public void addPointer(String pointerName, Step step) {
		this.pointers.put(pointerName, step);
	}

	public void run() throws IOException, FlowChartException {
		currentStep = steps.get(0);
		while (!exit && currentStep != null) {
			Step nextStep;
			try {
				nextStep = getNextStep();
			} catch (NoNextStep e) {
				nextStep = null;
			}
			alreadyInNext = false;
			currentStep.run(this, nextStep);
			if (!alreadyInNext) {
				currentStep = nextStep;
			}
		}
	}

	private Step getNextStep() throws NoNextStep {
		int index = steps.indexOf(currentStep) + 1;
		if (index == steps.size()) {
			throw new NoNextStep();
		}
		return steps.get(index);
	}

	public void runLabel(String stepName, Step nextStep) throws FlowChartException {
		executionStack.push(nextStep);
		jumpToLabel(stepName);
	}

	public void jumpToLabel(String stepName) throws FlowChartException {
		Step step = pointers.get(stepName);
		if (step == null) {
			throw new FlowChartException("Cannot find target step: " + stepName);
		}
		jumpToStep(step);
	}

	public void end() {
		try {
			Step nextStep = executionStack.pop();
			jumpToStep(nextStep);
		} catch (EmptyStackException e) {
			this.currentStep = null;
		}
	}

	public void exit() {
		exit = true;
	}

	public void jumpToStep(Step step) {
		currentStep = step;
		alreadyInNext = true;
	}

	private class NoNextStep extends Exception {
		private static final long serialVersionUID = 1L;

	}
}