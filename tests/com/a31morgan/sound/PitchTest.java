package com.a31morgan.sound;

import static org.junit.Assert.*;

import org.junit.Test;

public class PitchTest {

	@Test
	public void testIsRest() {
		for (Pitch p : Pitch.values()) {
			assertEquals((p == Pitch.REST), p.isRest());
		}
	}

	@Test
	public void testIncrement() {
		Pitch middleC = Pitch.C4;
		assertEquals(Pitch.C3, middleC.increment(-Pitch.INTERVALS_PER_OCTAVE));
		assertEquals(Pitch.B3, middleC.increment(-1));
		assertEquals(Pitch.C4$, middleC.increment(1));
		assertEquals(Pitch.C5, middleC.increment(Pitch.INTERVALS_PER_OCTAVE));
	}
	
	@Test
	public void testIncrement_ThatItReturnsNull_WhenThePitchIsNotCurrentlySupported() {
		assertNull(Pitch.values()[0].increment(-1));
		assertNull(Pitch.values()[Pitch.values().length - 1].increment(1));
	}

	@Test
	public void testGetFrequency() {
		Pitch[] inputs = {
				Pitch.C1, Pitch.D1, Pitch.E1,
				Pitch.F1, Pitch.G1, Pitch.A1, Pitch.B1,
				Pitch.C2, Pitch.C3, Pitch.C4, Pitch.C7,
		};
		double[] frequencies = {
				32.70, 36.71, 41.20,
				43.65, 49.00, 55.00, 61.74,
				65.41, 130.81, 261.63, 2093.00,
		};
		assertEquals(inputs.length, frequencies.length);
		for (int ii = 0; ii < inputs.length; ii++) {
			Pitch p = inputs[ii];
			double expected = frequencies[ii];
			assertEquals("Input pitch: " + p + "(ii=" + ii + ")", expected, p.getFrequency(), 0.01);
		}
	}

	@Test
	public void testGetPeriod() {
		Pitch[] inputs = {
				Pitch.C1, Pitch.C2, Pitch.C3, Pitch.C4,
		};
		double[] periods = {
				0.0306, 0.0153, 0.0076, 0.0038,
		};
		assertEquals(inputs.length, periods.length);
		for (int ii = 0; ii < inputs.length; ii++) {
			Pitch p = inputs[ii];
			double expected = periods[ii];
			assertEquals("Input pitch: " + p + "(ii=" + ii + ")", expected, p.getPeriod(), 0.0001);
		}
	}

	@Test
	public void testGetSineLength() {
		assertEquals(30.6, Pitch.C1.getSineLength(1000), 0.1);
		assertEquals(306.0, Pitch.C1.getSineLength(10000), 1.0);
		assertEquals(3060.0, Pitch.C1.getSineLength(100000), 10.0);
		
		assertEquals(76.0, Pitch.C3.getSineLength(10000), 1.0);
		assertEquals(477.0, Pitch.C7.getSineLength(1000000), 1.0);
	}
}
