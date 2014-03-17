package com.a31morgan.sound;

import static org.junit.Assert.*;

import org.junit.Test;

import com.a31morgan.sound.utils.ArrayUtils;

public class ArrayUtilsTest {

	@Test
	public void testRepeatToLength() {
		byte[] input = { 1, 2, 3 };
		assertArrayEquals(new byte[] { }, ArrayUtils.repeatToLength(input, 0));
		assertArrayEquals(new byte[] { 1, }, ArrayUtils.repeatToLength(input, 1));
		assertArrayEquals(new byte[] { 1, 2, }, ArrayUtils.repeatToLength(input, 2));
		assertArrayEquals(new byte[] { 1, 2, 3, }, ArrayUtils.repeatToLength(input, 3));
		assertArrayEquals(new byte[] { 1, 2, 3, 1, }, ArrayUtils.repeatToLength(input, 4));
		assertArrayEquals(new byte[] { 1, 2, 3, 1, 2, }, ArrayUtils.repeatToLength(input, 5));
		assertArrayEquals(new byte[] { 1, 2, 3, 1, 2, 3, }, ArrayUtils.repeatToLength(input, 6));
		assertArrayEquals(new byte[] { 1, 2, 3, 1, 2, 3, 1, }, ArrayUtils.repeatToLength(input, 7));
		assertArrayEquals(new byte[] { 1, 2, 3, 1, 2, 3, 1, 2, }, ArrayUtils.repeatToLength(input, 8));
		assertArrayEquals(new byte[] { 1, 2, 3, 1, 2, 3, 1, 2, 3, }, ArrayUtils.repeatToLength(input, 9));
		assertArrayEquals(new byte[] { 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, }, ArrayUtils.repeatToLength(input, 10));
	}

}
