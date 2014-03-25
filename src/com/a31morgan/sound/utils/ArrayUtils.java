package com.a31morgan.sound.utils;

import java.util.Arrays;


public class ArrayUtils {

	public static double[] copyOf(double[] input) {
		return Arrays.copyOf(input, input.length);
	}
	
	public static byte[] copyOf(byte[] input) {
		return Arrays.copyOf(input, input.length);
	}

	public static <T> T[] copyOf(T[] input) {
		return Arrays.copyOf(input, input.length);
	}

	public static double[] repeatToLength(double[] data, int length) {
		if (length == 0) {
			return new double[0];
		}
		double[] repeated = new double[length];
		int numFills = (length / data.length) + 1;
		for (int fillNum = 0; fillNum < numFills; fillNum++) {
			int start = fillNum * data.length;
			int numToCopy = Math.min(length - start, data.length);
			for (int ii = 0; ii < numToCopy; ii++) {
				repeated[start + ii] = data[ii];
			}
		}
		return repeated;
	}

	public static void reverse(final double[] buffer) {
		double b;
		for(int ii = 0; ii < buffer.length / 2; ii++) {
			b = buffer[ii];
			buffer[ii] = buffer[buffer.length - ii - 1];
			buffer[buffer.length - ii - 1] = b;
		}
	}
	
	public static byte[] convertToBytes(final double[] input, double scaleFactor) {
		byte[] output = new byte[input.length];
		byte value;
		for (int ii = 0; ii < input.length; ii++) {
			value = safeConvertToByte(input[ii] * scaleFactor);
			output[ii] = value;
		}
		return output;
	}
	
	private static byte safeConvertToByte(double d) {
		return (byte)(Math.min(Byte.MAX_VALUE, Math.max(Byte.MIN_VALUE, d)));
	}
}
