package com.a31morgan.sound.player;

import com.a31morgan.sound.Pitch;
import com.a31morgan.sound.utils.ArrayUtils;

public abstract class Filter implements IFilter {
	
	@Override
	public void applyNoteFilter(byte[] input) {}
	
	@Override
	public void applyStreamFilter(byte[] input) {}
	
	/* Factory methods */
	
	public static IFilter createFadeInFilter(final int fadeFrames) {
		return new Filter() {
			@Override
			public void applyNoteFilter(byte[] data) {
				fadeIn(data, fadeFrames);
			}
		};
	}

	public static IFilter createFadeOutFilter(final int fadeFrames) {
		return new Filter() {
			@Override
			public void applyNoteFilter(byte[] data) {
				fadeOut(data, fadeFrames);
			}
		};
	}
	
	public static IFilter createScaleFilter(final double scaleFactor) {
		return new Filter() {
			@Override
			public void applyNoteFilter(final byte[] input) {
				double value;
				for (int ii = 0; ii < input.length; ii++) {
					value = input[ii] * scaleFactor;
					input[ii] = safeConvertToByte(value);
				}
			}
		};
	}
	public static IFilter createNoiseCancellationFilter(final double thresholdFactor) {
		if (thresholdFactor < 0.0 || thresholdFactor > 1.0) {
			throw new IllegalArgumentException("thresholdFactor should be greater than 0.0, less than 1.0");
		}
		return new Filter() {
			@Override
			public void applyStreamFilter(final byte[] input) {
				int byteThreshold = (int)(thresholdFactor * Byte.MAX_VALUE);
				byte value;
				for (int ii = 0; ii < input.length; ii++) {
					value = input[ii];
					if (Math.abs(value) < byteThreshold) {
						double thresholdFactor = (double)value / byteThreshold;
						value = safeConvertToByte(value * thresholdFactor);
					}
					input[ii] =  value;
				}
			}
		};
	}
	
	public static IFilter createFadeInOutFilter(int fadeMillis, int sampleRate) {
		return createFadeInOutFilter(fadeMillis, fadeMillis, sampleRate);
	}
	
	public static IFilter createFadeInOutFilter(int fadeInMillis, int fadeOutMillis, int sampleRate) {
		final int fadeInLength = sampleRate * fadeInMillis / 1000;
		final int fadeOutLength = sampleRate * fadeInMillis / 1000;
		return new Filter() {
			@Override
			public void applyNoteFilter(byte[] data) {
				fadeInAndOut(fadeInLength, fadeOutLength, data);
			}
		};
	}

	public static IFilter createEchoFilter(double volumeFraction, double offsetMillis) {
		return createMultiEchoFilter(new double[] { volumeFraction }, new double[] { offsetMillis });
	}

	protected static IFilter createEchoFilter(double volumeFraction, int offsetFrames) {
		return createMultiEchoFilter(new double[] { volumeFraction }, new int[] { offsetFrames });
	}
	
	public static IFilter createMultiEchoFilter(final double[] volumeFractions, final double[] offsetMillis) {
		int[] offsetFrames = new int[offsetMillis.length];
		for (int ii = 0; ii < offsetMillis.length; ii++) {
			offsetFrames[ii] = (int)(Pitch.SAMPLE_RATE * offsetMillis[ii] / 1000.0);
		}
		return createMultiEchoFilter(volumeFractions, offsetFrames);
	}
	
	public static IFilter createMultiEchoFilter(final double[] volumeFractions, final int[] offsetFrames) {
		if (volumeFractions.length != offsetFrames.length) {
			throw new IllegalArgumentException("volumeFractions.length != offsetMillis.length");
		}
		return new Filter() {
			@Override
			public void applyStreamFilter(byte[] input) {
				for (int ii = 0; ii < volumeFractions.length; ii++) {
					applyEcho(input, volumeFractions[ii], offsetFrames[ii]);
				}
			}
		};
	}
	
	/* Filter helper (static) methods */
	
	public static void applyEcho(byte[] input, double volumeFraction, int offsetLength) {
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
		for (int ii = 0; ii < input.length; ii++) {
			input[ii] = output[ii];
		}
	}
	
	public static void fadeInAndOut(int fadeInLength, int fadeOutLength, byte[] data) {
		fadeIn(data, fadeInLength);
		fadeOut(data, fadeOutLength);
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
