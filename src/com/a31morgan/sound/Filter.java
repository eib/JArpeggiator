package com.a31morgan.sound;

import java.util.Arrays;

public abstract class Filter {
	
	public abstract byte[] applyFilter(byte[] data, int start, int length);

	public static Filter FADE_IN = new Filter() {
		@Override
		public byte[] applyFilter(byte[] data, int start, int length) {
			byte[] copy = Arrays.copyOfRange(data, start, length);
			fadeIn(copy, length);
			return copy;
		}
	};

	public static Filter FADE_OUT = new Filter() {
		@Override
		public byte[] applyFilter(byte[] data, int start, int length) {
			byte[] copy = Arrays.copyOfRange(data, start, length);
			fadeOut(copy, length);
			return copy;
		}
	};
	
	public static Filter createFadeInOutFilter(int fadeMillis, int sampleRate) {
		return createFadeInOutFilter(fadeMillis, fadeMillis, sampleRate);
	}
	
	public static Filter createFadeInOutFilter(int fadeInMillis, int fadeOutMillis, int sampleRate) {
		final int fadeInLength = sampleRate * fadeInMillis / 1000;
		final int fadeOutLength = sampleRate * fadeInMillis / 1000;
		return new Filter() {
			@Override
			public byte[] applyFilter(byte[] data, int start, int length) {
				return fadeInAndOut(fadeInLength, fadeOutLength, data, start, length);
			}
		};
	}
	
	public static byte[] fadeInAndOut(int fadeInLength, int fadeOutLength, byte[] data, int start, int length) {
		byte[] copy = Arrays.copyOfRange(data, start, length);
		fadeIn(copy, fadeInLength);
		fadeOut(copy, fadeOutLength);
		return copy;
	}

	private static void fadeIn(byte[] buffer, int length) {
		double fadeBytes = length;
		for (int ii = 0; ii < length; ii++) {
			double fadeMultiple = (double)ii / fadeBytes;
			buffer[ii] = (byte)((double)buffer[ii] * fadeMultiple);
		}
	}

	private static void fadeOut(byte[] buffer, int length) {
		ArrayUtils.reverse(buffer);
		fadeIn(buffer, length);
		ArrayUtils.reverse(buffer);
	}
}
