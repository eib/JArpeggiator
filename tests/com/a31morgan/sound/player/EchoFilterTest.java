package com.a31morgan.sound.player;

import static org.junit.Assert.*;

import org.junit.Test;

public class EchoFilterTest {

	@Test
	public void testApplyStreamFilter() {
		EchoFilter filter = new EchoFilter(0.5, 1);
		double[] input = {
				1, 0, 2, 0, 3, 0,
		};
		double[] expected = {
				1, 0.5, 2, 1, 3, 1.5,
		};
		filter.applyNoteFilter(input);
		assertArrayEquals(expected, input, 0.0001);
	}
	
	@Test
	public void testApplyStreamFilter_ThatItWorksAcrossMultipleStreams() {
		FilterOutputStream os = new FilterOutputStream(new EchoFilter(0.5, 3));
		os.write(new double[] { 1, 0, });
		os.write(new double[] { 2, 0, });
		os.write(new double[] { 3, 0, });
		os.write(new double[] { 0, 0, });
		double[] expected = {
				1, 0,
				2, 0.5,
				3, 1,
				0, 1.5,
		};
		assertArrayEquals(expected, os.toDoubleArray(), 0.0001);
	}
}
