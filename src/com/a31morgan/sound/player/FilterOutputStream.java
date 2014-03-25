package com.a31morgan.sound.player;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.a31morgan.sound.utils.ArrayUtils;

public class FilterOutputStream {

	private final IFilter[] filters;
	private List<double[]> dataChunks = new ArrayList<>();
	private double scaleFactor = 64.0;
	
	public FilterOutputStream(IFilter... filters) {
		this.filters = filters;
	}

	public void write(double[] data) {
		for (IFilter filter : this.filters) {
			filter.applyNoteFilter(data);	
		}
		dataChunks.add(data);
	}
	
	public double[] toDoubleArray() {
		int size = 0;
		for (double[] data : this.dataChunks) {
			size += data.length;
		}
		double[] output = new double[size];
		int index = 0;
		for (double[] data : this.dataChunks) {
			for (int ii = 0; ii < data.length; ii++) {
				output[index++] = data[ii];
			}
		}
		return output;
	}

	public byte[] toByteArray() {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		for (double[] data : dataChunks) {
			for (IFilter filter : this.filters) {
				filter.applyStreamFilter(data);
			}
			try {
				os.write(ArrayUtils.convertToBytes(data, scaleFactor));
			} catch (IOException e) { }
		}
		return os.toByteArray();
	}
}
