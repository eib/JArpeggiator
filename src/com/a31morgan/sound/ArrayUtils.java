package com.a31morgan.sound;


public class ArrayUtils {

	public static byte[] repeatToLength(byte[] data, int length) {
		if (length == 0) {
			return new byte[0];
		}
		byte[] repeated = new byte[length];
		int numFills = (length / data.length) + 1;
		for (int fillNum = 0; fillNum < numFills; fillNum++) {
			int start = fillNum * data.length;
			int numBytesToCopy = Math.min(length - start, data.length);
			for (int ii = 0; ii < numBytesToCopy; ii++) {
				repeated[start + ii] = data[ii];
			}
		}
		return repeated;
	}

	public static void reverse(final byte[] buffer) {
		byte b;
		for(int ii = 0; ii < buffer.length / 2; ii++) {
			b = buffer[ii];
			buffer[ii] = buffer[buffer.length - ii - 1];
			buffer[buffer.length - ii - 1] = b;
		}
	}
}
