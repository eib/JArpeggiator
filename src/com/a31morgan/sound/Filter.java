package com.a31morgan.sound;

import java.util.Arrays;
import java.util.Random;

public abstract class Filter {
	
	public byte[] applyNoteFilter(byte[] input, int start, int length) {
		return Arrays.copyOfRange(input, start, start + length);
	}
	public byte[] applyStreamFilter(byte[] input) {
		return input;
	}
	
	/* Factory methods */
	
	public static Filter createFadeInFilter(final int fadeFrames) {
		return new Filter() {
			@Override
			public byte[] applyNoteFilter(byte[] data, int start, int length) {
				byte[] copy = Arrays.copyOfRange(data, start, start + length);
				fadeIn(copy, fadeFrames);
				return copy;
			}
		};
	}

	public static Filter createFadeOutFilter(final int fadeFrames) {
		return new Filter() {
			@Override
			public byte[] applyNoteFilter(byte[] data, int start, int length) {
				byte[] copy = Arrays.copyOfRange(data, start, start + length);
				fadeOut(copy, fadeFrames);
				return copy;
			}
		};
	}
	
	public static Filter createScaleFilter(final double scaleFactor) {
		return new Filter() {
			@Override
			public byte[] applyNoteFilter(byte[] input, int start, int length) {
				byte[] output = new byte[length];
				double value;
				for (int ii = 0; ii < length; ii++) {
					value = input[ii + start] * scaleFactor;
					output[ii] = safeConvertToByte(value);
				}
				return output;
			}
		};
	}
	public static Filter createNoiseCancellationFilter(final double thresholdFactor) {
		if (thresholdFactor < 0.0 || thresholdFactor > 1.0) {
			throw new IllegalArgumentException("thresholdFactor should be greater than 0.0, less than 1.0");
		}
		return new Filter() {
			@Override
			public byte[] applyStreamFilter(byte[] input) {
				byte[] output = Arrays.copyOf(input, input.length);
				int byteThreshold = (int)(thresholdFactor * Byte.MAX_VALUE);
				byte value;
				for (int ii = 0; ii < input.length; ii++) {
					value = input[ii];
					if (Math.abs(value) < byteThreshold) {
						double thresholdFactor = (double)value / byteThreshold;
						value = safeConvertToByte(value * thresholdFactor);
					}
					output[ii] =  value;
				}
				return output;
			}
		};
	}
	
	public static Filter createNoiseFilter(final double fraction) {
		return new Filter() {
			private Random r = new Random(System.currentTimeMillis());
			@Override
			public byte[] applyStreamFilter(byte[] input) {
				byte[] copy = Arrays.copyOf(input, input.length);
				double value;
				for (int ii = 0; ii < input.length; ii++) {
					value = input[ii] + fraction * r.nextInt(Byte.MAX_VALUE) - (Byte.MAX_VALUE / 2);
					copy[ii] = safeConvertToByte(value);
				}
				return copy;
			}
		};
	}
	
	public static Filter createFadeInOutFilter(int fadeMillis, int sampleRate) {
		return createFadeInOutFilter(fadeMillis, fadeMillis, sampleRate);
	}
	
	public static Filter createFadeInOutFilter(int fadeInMillis, int fadeOutMillis, int sampleRate) {
		final int fadeInLength = sampleRate * fadeInMillis / 1000;
		final int fadeOutLength = sampleRate * fadeInMillis / 1000;
		return new Filter() {
			@Override
			public byte[] applyNoteFilter(byte[] data, int start, int length) {
				return fadeInAndOut(fadeInLength, fadeOutLength, data, start, length);
			}
		};
	}

	public static Filter createEchoFilter(double volumeFraction, double offsetMillis) {
		return createMultiEchoFilter(new double[] { volumeFraction }, new double[] { offsetMillis });
	}

	protected static Filter createEchoFilter(double volumeFraction, int offsetFrames) {
		return createMultiEchoFilter(new double[] { volumeFraction }, new int[] { offsetFrames });
	}
	
	public static Filter createMultiEchoFilter(final double[] volumeFractions, final double[] offsetMillis) {
		int[] offsetFrames = new int[offsetMillis.length];
		for (int ii = 0; ii < offsetMillis.length; ii++) {
			offsetFrames[ii] = (int)(Pitch.SAMPLE_RATE * offsetMillis[ii] / 1000.0);
		}
		return createMultiEchoFilter(volumeFractions, offsetFrames);
	}
	
	public static Filter createMultiEchoFilter(final double[] volumeFractions, final int[] offsetFrames) {
		if (volumeFractions.length != offsetFrames.length) {
			throw new IllegalArgumentException("volumeFractions.length != offsetMillis.length");
		}
		return new Filter() {
			@Override
			public byte[] applyStreamFilter(byte[] input) {
				byte[] output = input;
				for (int ii = 0; ii < volumeFractions.length; ii++) {
					input = output = applyEcho(input, volumeFractions[ii], offsetFrames[ii]);
				}
				return output;
			}
		};
	}
	
	/* Filter helper (static) methods */
	
	protected static byte[] applyEcho(byte[] input, double volumeFraction, int offsetLength) {
		byte[] output = new byte[input.length];
		offsetLength = Math.min(offsetLength, input.length);
		for (int ii = 0; ii < offsetLength; ii++) {
			output[ii] = input[ii];
		}
		double value;
		for (int ii = offsetLength; ii < input.length; ii++) {
			value = input[ii] + volumeFraction * input[ii - offsetLength];
			output[ii] = safeConvertToByte(value);
		}
		return output;
	}
	
	public static byte[] fadeInAndOut(int fadeInLength, int fadeOutLength, byte[] data, int start, int length) {
		byte[] copy = Arrays.copyOfRange(data, start, start + length);
		fadeIn(copy, fadeInLength);
		fadeOut(copy, fadeOutLength);
		return copy;
	}

	private static void fadeIn(byte[] buffer, final int fadeFrames) {
		int fadeLength = Math.min(fadeFrames, buffer.length);
		for (int ii = 0; ii < fadeLength; ii++) {
			double fadeMultiple = (double)ii / (double)fadeFrames;
			buffer[ii] = (byte)((double)buffer[ii] * fadeMultiple);
		}
	}

	private static void fadeOut(byte[] buffer, int fadeFrames) {
		ArrayUtils.reverse(buffer);
		fadeIn(buffer, fadeFrames);
		ArrayUtils.reverse(buffer);
	}
	
	private static byte safeConvertToByte(double d) {
		return (byte)(Math.min(Byte.MAX_VALUE, Math.max(Byte.MIN_VALUE, d)));
	}
}
