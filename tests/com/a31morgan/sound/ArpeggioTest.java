package com.a31morgan.sound;

import static com.a31morgan.sound.Pitch.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ArpeggioTest {
	
	@Test
	public void testGetNotes_ThatIntervalsMayContainNegativeNumbers() {
		List<Pitch> expected = Arrays.asList(new Pitch[] {
				C1, D1$, D1, F1,
				E1, G1, F1$, A1,
		});
		List<Pitch> actual = new Arpeggio(new int[] { 3, -1, 3, -1 }).getNotes(C1, 2);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetNotes_ThatItNeedntSpanAnEntireOctave() {
		List<Pitch> expected = Arrays.asList(new Pitch[] {
				C1, D1, E1, F1,
				G1, A1, B1, C2, //two "arpeggios" == one real octave
		});
		List<Pitch> actual = new Arpeggio(new int[] { 2, 2, 1, 2 }).getNotes(C1, 2);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetNotes_WithMajorArpeggio() {
		List<Pitch> expected = Arrays.asList(new Pitch[] { C1, E1, G1 });
		List<Pitch> actual = Arpeggio.MAJOR.getNotes(C1, 1);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetNotes_ThatItRespectsNumOctaves() {
		{
			List<Pitch> expected = Arrays.asList(new Pitch[] {
					C1, E1, G1, C2, E2, G2,
					C3, E3, G3,
			});
			List<Pitch> actual = Arpeggio.MAJOR.getNotes(C1, 3);
			assertEquals(expected, actual);
		}
		{
			List<Pitch> expected = Arrays.asList(new Pitch[] {
					C1, E1, G1, C2, E2, G2,
					C3, E3, G3, C4, E4, G4,
					C5, E5, G5,
			});
			List<Pitch> actual = Arpeggio.MAJOR.getNotes(C1, 5);
			assertEquals(expected, actual);
		}
	}

	@Test
	public void testGetNotes_ThatItIsLimitedBySupportedPitches() {
		Pitch maxPitch = Pitch.values()[Pitch.values().length - 1];
		List<Pitch> expected = Arrays.asList(new Pitch[] { maxPitch });
		List<Pitch> actual = Arpeggio.MAJOR.getNotes(maxPitch, 3);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetNotes_WithMinorArpeggio() {
		List<Pitch> expected = Arrays.asList(new Pitch[] { C1, D1$, G1 });
		List<Pitch> actual = Arpeggio.MINOR.getNotes(C1, 1);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetNotes_WithDiminishedArpeggio() {
		List<Pitch> expected = Arrays.asList(new Pitch[] { C1, D1$, F1$, A1, });
		List<Pitch> actual = Arpeggio.DIMINISHED.getNotes(C1, 1);
		assertEquals(expected, actual);
	}
}
