package com.a31morgan.sound;

import static org.junit.Assert.*;

import org.junit.Test;

import com.a31morgan.sound.utils.ArrayUtils;

public class ArrayUtilsTest {

	@Test
	public void testRepeatToLength() {
		double[] input = { 1, 2, 3 };
		assertArrayEquals(new double[] { }, ArrayUtils.repeatToLength(input, 0), 0.001);
		assertArrayEquals(new double[] { 1, }, ArrayUtils.repeatToLength(input, 1), 0.001);
		assertArrayEquals(new double[] { 1, 2, }, ArrayUtils.repeatToLength(input, 2), 0.001);
		assertArrayEquals(new double[] { 1, 2, 3, }, ArrayUtils.repeatToLength(input, 3), 0.001);
		assertArrayEquals(new double[] { 1, 2, 3, 1, }, ArrayUtils.repeatToLength(input, 4), 0.001);
		assertArrayEquals(new double[] { 1, 2, 3, 1, 2, }, ArrayUtils.repeatToLength(input, 5), 0.001);
		assertArrayEquals(new double[] { 1, 2, 3, 1, 2, 3, }, ArrayUtils.repeatToLength(input, 6), 0.001);
		assertArrayEquals(new double[] { 1, 2, 3, 1, 2, 3, 1, }, ArrayUtils.repeatToLength(input, 7), 0.001);
		assertArrayEquals(new double[] { 1, 2, 3, 1, 2, 3, 1, 2, }, ArrayUtils.repeatToLength(input, 8), 0.001);
		assertArrayEquals(new double[] { 1, 2, 3, 1, 2, 3, 1, 2, 3, }, ArrayUtils.repeatToLength(input, 9), 0.001);
		assertArrayEquals(new double[] { 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, }, ArrayUtils.repeatToLength(input, 10), 0.001);
	}

}
