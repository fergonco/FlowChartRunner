package org.fergonco.flowchartrunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FlowChartRunner {
	public static void main(String[] args) throws IOException, FlowChartException {
		Parser parser = new Parser();
		File last = new File(System.getProperty("user.home"), ".flowchartrunner");
		String file;
		if (args.length == 0 && last.exists()) {
			byte[] buffer = new byte[(int) last.length()];

			FileInputStream is = new FileInputStream(last);
			int n;
			try {
				n = is.read(buffer);
			} finally {
				is.close();
			}
			if (n < buffer.length) {
				throw new IOException("Cannot read ~/.flowcharrunner");
			}
			file = new String(buffer);
		} else if (args.length > 0) {
			file = args[0];
		} else {
			throw new FlowChartException("No argument provided and ~/.flowchartrunner does not exist");
		}
		FileOutputStream fos = new FileOutputStream(last);
		try {
			fos.write(file.getBytes());
		} finally {
			fos.close();
		}
		FlowChart flowChart = parser.parse(file);
		flowChart.run();
	}
}
