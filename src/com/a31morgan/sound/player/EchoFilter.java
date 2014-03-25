package com.a31morgan.sound.player;

import java.util.ArrayList;
import java.util.List;

public class EchoFilter extends Filter {
	
	private final double volumeFraction;
	private final int frameOffset;
	private double[] previousFrames;
	private int frameIndex = 0;
	
	public EchoFilter(double volumeFraction, double offsetMillis, int sampleRate) {
		this(volumeFraction, (int)(offsetMillis * sampleRate / 1000.0));
	}
	
	public EchoFilter(double volumeFraction, int frameOffset) {
		this.volumeFraction = volumeFraction;
		this.frameOffset = frameOffset;
		this.previousFrames = new double[frameOffset];
	}

	@Override
	public void applyNoteFilter(double[] input) {
		for (int ii = 0; ii < input.length; ii++) {
			input[ii] += nextEchoSample(input[ii]);
		}
	}
	
	protected double nextEchoSample(double input) {
		double output = previousFrames[frameIndex];
		previousFrames[frameIndex] = input * volumeFraction;
		frameIndex = (frameIndex + 1) % frameOffset;
		return output;
	}
	
	public static IFilter createMultiEchoFilter(final double[] volumeFractions, final double[] offsetMillis, int sampleRate) {
		if (volumeFractions.length != offsetMillis.length) {
			throw new IllegalArgumentException("volumeFractions.length != offsetMillis.length");
		}
		int[] offsetFrames = new int[offsetMillis.length];
		for (int ii = 0; ii < offsetMillis.length; ii++) {
			offsetFrames[ii] = (int)(sampleRate * offsetMillis[ii] / 1000.0);
		}
		return createMultiEchoFilter(volumeFractions, offsetFrames);
	}
	
	public static IFilter createMultiEchoFilter(final double[] volumeFractions, final int[] offsetFrames) {
		if (volumeFractions.length != offsetFrames.length) {
			throw new IllegalArgumentException("volumeFractions.length != offsetFrames.length");
		}
		List<IFilter> filters = new ArrayList<>();
		for (int ii = 0; ii < volumeFractions.length; ii++) {
			filters.add(new EchoFilter(volumeFractions[ii], offsetFrames[ii]));
		}
		return new CompositeFilter(filters);
	}
}
