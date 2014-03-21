package com.a31morgan.sound;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import com.a31morgan.sound.player.Filter;
import com.a31morgan.sound.player.IFilter;
import com.a31morgan.sound.utils.ArrayUtils;

public class FilterTest {
	
	@Test
	public void testCreateMultiEchoFilter() {
		IFilter f = Filter.createMultiEchoFilter(new double[] { 1, 2, 3 }, new int[] { 1, 2, 3 });
		byte[] input = {
				1, 0, 0, 0, 0,
				0, 0, 0, 0, 0,
				};
		byte[] expected = {
				1, 1, 2, 5, 3,
				6, 6, 0, 0, 0,
				};
		byte[] output = ArrayUtils.copyOf(input);
		f.applyStreamFilter(output);
		assertArrayEquals(expected, output);
	}

	@Test
	public void testApplyEcho_WithFractionalFilter() {
		byte[] input = {
				2, 4, 6, 8, 10,
				0, 0, 0, 0, 0,
				};
		byte[] expected = {
				2, 4, 6, 8, 10,
				1, 2, 3, 4, 5,
				};
		byte[] output = ArrayUtils.copyOf(input);
		Filter.applyEcho(output, 0.5, 5);
		assertArrayEquals(expected, output);
	}

	@Test
	public void testApplyEcho_ThatItReturnsInputBufferWithoutExceptions_WhenBufferIsSmallerThanOffsetLength() {
		byte[] input = {
				1, 2, 3,
				};
		byte[] expected = {
				1, 2, 3
				};
		byte[] output = ArrayUtils.copyOf(input);
		Filter.applyEcho(output, 0.5, 50);
		assertArrayEquals(expected, output);
	}

	@Test
	public void testApplyEcho_ThatItAddsToExistingSamples() {
		byte[] input = {
				1, 2, 3, 4, 5,
				1, 2, 3, 4, 5,
				};
		byte[] expected = {
				1, 2, 3, 4, 5,
				8, 16, 24, 32, 40,
				};
		byte[] output = ArrayUtils.copyOf(input);
		Filter.applyEcho(output, 7.0, 5);
		assertArrayEquals(expected, output);
	}

	@Test
	public void testApplyEcho_ThatItRepeats() {
		byte[] input = {
				0, 1, 2, 3, 4,
				5, 6, 7, 8, 9,
				10, 11, 12, 13, 14,
				15, 16, 17, 18, 19,
				20,
				};
		byte[] expected = {
				0, 1, 2, 3, 4,
				5, 7, 9, 11, 13,
				15, 17, 19, 21, 23,
				25, 27, 29, 31, 33,
				35,
				};
		byte[] output = ArrayUtils.copyOf(input);
		Filter.applyEcho(output, 1.0, 5);
		assertArrayEquals(expected, output);
	}

	@Test
	public void testApplyEcho_WithIdentityFilter() {
		byte[] input = {
				1, 2, 3, 4, 5,
				0, 0, 0, 0, 0,
				};
		byte[] expected = {
				1, 2, 3, 4, 5,
				1, 2, 3, 4, 5,
				};
		byte[] output = ArrayUtils.copyOf(input);
		Filter.applyEcho(output, 1.0, 5);
		assertArrayEquals(expected, output);
	}
	
	@Test
	public void testApplyEcho_WithMultiplicativeFilter() {
		byte[] input = {
				2, 4, 6, 8, 10,
				0, 0, 0, 0, 0,
				};
		byte[] expected = {
				2, 4, 6, 8, 10,
				4, 8, 12, 16, 20,
				};
		byte[] output = ArrayUtils.copyOf(input);
		Filter.applyEcho(output, 2.0, 5);
		assertArrayEquals(expected, output);
	}
	
	@Test
	public void testApplyEcho_WithMultiplicativeFilter_ThatLargeValuesDontCauseByteOverFlow() {
		byte[] input = {
				123, 124, 125, 126, 127,
				0, 0, 0, 0, 0,
				};
		byte[] expected = {
				123, 124, 125, 126, 127,
				Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE,
				};
		byte[] output = ArrayUtils.copyOf(input);
		Filter.applyEcho(output, 2.0, 5);
		assertArrayEquals(expected, output);
	}
	
	@Test
	public void testApplyEcho_WithMultiplicativeFilter_ThatNegativeValuesDontCauseByteUnderFlow() {
		byte[] input = {
				123, 124, 125, 126, 127,
				0, 0, 0, 0, 0,
				};
		byte[] expected = {
				123, 124, 125, 126, 127,
				Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE,
				};
		byte[] output = ArrayUtils.copyOf(input);
		Filter.applyEcho(output, -2.0, 5);
		assertArrayEquals(expected, output);
	}
}
