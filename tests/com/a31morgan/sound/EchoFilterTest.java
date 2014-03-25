package com.a31morgan.sound;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import com.a31morgan.sound.player.EchoFilter;
import com.a31morgan.sound.player.IFilter;

public class EchoFilterTest {
	
	protected static void applyEcho(double[] input_output, double volumeFraction, int framesOffset) {
		new EchoFilter(volumeFraction, framesOffset).applyNoteFilter(input_output);
	}
	
	@Test
	public void testCreateMultiEchoFilter() {
		IFilter f = EchoFilter.createMultiEchoFilter(new double[] { 1, 2, 3 }, new int[] { 1, 2, 3 });
		double[] input_output = {
				1, 0, 0, 0, 0,
				0, 0, 0, 0, 0,
				};
		double[] expected = {
				1, 1, 2, 5, 3,
				6, 6, 0, 0, 0,
				};
		f.applyNoteFilter(input_output);
		assertArrayEquals(expected, input_output, 0.01);
	}

	@Test
	public void testApplyEcho_WithFractionalFilter() {
		double[] input_output = {
				2, 4, 6, 8, 10,
				0, 0, 0, 0, 0,
				};
		double[] expected = {
				2, 4, 6, 8, 10,
				1, 2, 3, 4, 5,
				};
		applyEcho(input_output, 0.5, 5);
		assertArrayEquals(expected, input_output, 0.01);
	}

	@Test
	public void testApplyEcho_ThatItReturnsInputBufferWithoutExceptions_WhenBufferIsSmallerThanOffsetLength() {
		double[] input_output = {
				1, 2, 3,
				};
		double[] expected = {
				1, 2, 3
				};
		applyEcho(input_output, 0.5, 50);
		assertArrayEquals(expected, input_output, 0.01);
	}

	@Test
	public void testApplyEcho_ThatItAddsToExistingSamples() {
		double[] input_output = {
				1, 2, 3, 4, 5,
				1, 2, 3, 4, 5,
				};
		double[] expected = {
				1, 2, 3, 4, 5,
				8, 16, 24, 32, 40,
				};
		applyEcho(input_output, 7.0, 5);
		assertArrayEquals(expected, input_output, 0.01);
	}

	@Test
	public void testApplyEcho_ThatItRepeats() {
		double[] input_output = {
				0, 1, 2, 3, 4,
				5, 6, 7, 8, 9,
				10, 11, 12, 13, 14,
				15, 16, 17, 18, 19,
				20,
				};
		double[] expected = {
				0, 1, 2, 3, 4,
				5, 7, 9, 11, 13,
				15, 17, 19, 21, 23,
				25, 27, 29, 31, 33,
				35,
				};
		applyEcho(input_output, 1.0, 5);
		assertArrayEquals(expected, input_output, 0.01);
	}

	@Test
	public void testApplyEcho_WithIdentityFilter() {
		double[] input_output = {
				1, 2, 3, 4, 5,
				0, 0, 0, 0, 0,
				};
		double[] expected = {
				1, 2, 3, 4, 5,
				1, 2, 3, 4, 5,
				};
		applyEcho(input_output, 1.0, 5);
		assertArrayEquals(expected, input_output, 0.01);
	}
	
	@Test
	public void testApplyEcho_WithMultiplicativeFilter() {
		double[] input_output = {
				2, 4, 6, 8, 10,
				0, 0, 0, 0, 0,
				};
		double[] expected = {
				2, 4, 6, 8, 10,
				4, 8, 12, 16, 20,
				};
		applyEcho(input_output, 2.0, 5);
		assertArrayEquals(expected, input_output, 0.01);
	}
}
