package com.a31morgan.sound;

import static com.a31morgan.sound.Pitch.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ArpeggiatorTest {

	@Test
	public void testDownUp() {
		{
			Arpeggiator a = Arpeggiator.DOWN_UP;
			List<Pitch> actual = a.getArpeggio(Arpeggio.MAJOR, C1, 1);
			List<Pitch> expected = Arrays.asList(new Pitch[] { G1, E1, C1, E1, });
			assertEquals(expected, actual);
		}
		{
			Arpeggiator a = Arpeggiator.DOWN_UP;
			List<Pitch> actual = a.getArpeggio(Arpeggio.MAJOR, C1, 2);
			List<Pitch> expected = Arrays.asList(new Pitch[] { G2, E2, C2, G1, E1, C1, E1, G1, C2, E2, });
			assertEquals(expected, actual);
		}
	}

	@Test
	public void testUpDown() {
		{
			Arpeggiator a = Arpeggiator.UP_DOWN;
			List<Pitch> actual = a.getArpeggio(Arpeggio.MAJOR, Pitch.C1, 1);
			List<Pitch> expected = Arrays.asList(new Pitch[] { C1, E1, G1, E1, });
			assertEquals(expected, actual);
		}
		{
			Arpeggiator a = Arpeggiator.UP_DOWN;
			List<Pitch> actual = a.getArpeggio(Arpeggio.MAJOR, Pitch.C1, 2);
			List<Pitch> expected = Arrays.asList(new Pitch[] { C1, E1, G1, C2, E2, G2, E2, C2, G1, E1, });
			assertEquals(expected, actual);
		}
	}

	@Test
	public void testUp() {
		{
			Arpeggiator a = Arpeggiator.UP;
			List<Pitch> actual = a.getArpeggio(Arpeggio.MAJOR, Pitch.C1, 1);
			List<Pitch> expected = Arrays.asList(new Pitch[] { C1, E1, G1, });
			assertEquals(expected, actual);
		}
		{
			Arpeggiator a = Arpeggiator.UP;
			List<Pitch> actual = a.getArpeggio(Arpeggio.MAJOR, Pitch.C1, 3);
			List<Pitch> expected = Arrays.asList(new Pitch[] { C1, E1, G1, C2, E2, G2, C3, E3, G3, });
			assertEquals(expected, actual);
		}
	}

	@Test
	public void testDown() {
		{
			Arpeggiator a = Arpeggiator.DOWN;
			List<Pitch> actual = a.getArpeggio(Arpeggio.MAJOR, Pitch.C1, 1);
			List<Pitch> expected = Arrays.asList(new Pitch[] { G1, E1, C1, });
			assertEquals(expected, actual);
		}
		{
			Arpeggiator a = Arpeggiator.DOWN;
			List<Pitch> actual = a.getArpeggio(Arpeggio.MAJOR, Pitch.C1, 3);
			List<Pitch> expected = Arrays.asList(new Pitch[] { G3, E3, C3, G2, E2, C2, G1, E1, C1, });
			assertEquals(expected, actual);
		}
	}
}
