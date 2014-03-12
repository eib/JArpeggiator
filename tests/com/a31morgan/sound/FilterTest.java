package com.a31morgan.sound;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class FilterTest {
	
	@Test
	public void testCreateMultiEchoFilter() {
		Filter f = Filter.createMultiEchoFilter(new double[] { 1, 2, 3 }, new int[] { 1, 2, 3 });
		byte[] input = {
				1, 0, 0, 0, 0,
				0, 0, 0, 0, 0,
				};
		byte[] expected = {
				1, 1, 2, 5, 3,
				6, 6, 0, 0, 0,
				};
		byte[] actual = f.applyStreamFilter(input);
		assertArrayEquals(expected, actual);
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
		byte[] actual = Filter.applyEcho(input, 0.5, 5);
		assertArrayEquals(expected, actual);
	}

	@Test
	public void testApplyEcho_ThatItReturnsInputBufferWithoutExceptions_WhenBufferIsSmallerThanOffsetLength() {
		byte[] input = {
				1, 2, 3,
				};
		byte[] expected = {
				1, 2, 3
				};
		byte[] actual = Filter.applyEcho(input, 0.5, 50);
		assertArrayEquals(expected, actual);
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
		byte[] actual = Filter.applyEcho(input, 7.0, 5);
		assertArrayEquals(expected, actual);
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
		byte[] actual = Filter.applyEcho(input, 1.0, 5);
		assertArrayEquals(expected, actual);
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
		byte[] actual = Filter.applyEcho(input, 1.0, 5);
		assertArrayEquals(expected, actual);
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
		byte[] actual = Filter.applyEcho(input, 2.0, 5);
		assertArrayEquals(expected, actual);
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
		byte[] actual = Filter.applyEcho(input, 2.0, 5);
		assertArrayEquals(expected, actual);
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
		byte[] actual = Filter.applyEcho(input, -2.0, 5);
		assertArrayEquals(expected, actual);
	}
}
