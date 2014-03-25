package com.a31morgan.sound.player;

import com.a31morgan.sound.utils.ArrayUtils;

public abstract class Filter implements IFilter {
	
	@Override
	public void applyNoteFilter(double[] input) {}
	
	@Override
	public void applyStreamFilter(double[] input) {}
	
	/* Factory methods */
	
	public static IFilter createFadeInOutFilter(int fadeMillis, int sampleRate) {
		final int fadeInLength = sampleRate * fadeMillis / 1000;
		final int fadeOutLength = sampleRate * fadeMillis / 1000;
		return new Filter() {
			@Override
			public void applyNoteFilter(double[] data) {
				Filter.fadeInAndOut(fadeInLength, fadeOutLength, data);
			}
		};
	}
	
	public static IFilter createFadeInFilter(final int fadeFrames) {
		return new Filter() {
			@Override
			public void applyNoteFilter(double[] data) {
				fadeIn(data, fadeFrames);
			}
		};
	}

	public static IFilter createFadeOutFilter(final int fadeFrames) {
		return new Filter() {
			@Override
			public void applyNoteFilter(double[] data) {
				fadeOut(data, fadeFrames);
			}
		};
	}
	
	public static IFilter createScaleFilter(final double scaleFactor) {
		return new Filter() {
			@Override
			public void applyNoteFilter(final double[] input) {
				double value;
				for (int ii = 0; ii < input.length; ii++) {
					value = input[ii] * scaleFactor;
					input[ii] = value;
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
			public void applyStreamFilter(final double[] input) {
				double value;
				for (int ii = 0; ii < input.length; ii++) {
					value = input[ii];
					if (Math.abs(value) < thresholdFactor) {
						value = value * thresholdFactor;
					}
					input[ii] =  value;
				}
			}
		};
	}
	
	/* Filter helper (static) methods */
	
	public static void fadeInAndOut(int fadeInLength, int fadeOutLength, double[] data) {
		fadeIn(data, fadeInLength);
		fadeOut(data, fadeOutLength);
	}

	private static void fadeIn(double[] buffer, final int fadeFrames) {
		int fadeLength = Math.min(fadeFrames, buffer.length);
		double fadeMultiplier;
		for (int ii = 0; ii < fadeLength; ii++) {
			fadeMultiplier = (double)ii / (double)fadeFrames;
			buffer[ii] = buffer[ii] * fadeMultiplier;
		}
	}

	private static void fadeOut(double[] buffer, int fadeFrames) {
		ArrayUtils.reverse(buffer);
		fadeIn(buffer, fadeFrames);
		ArrayUtils.reverse(buffer);
	}
}
