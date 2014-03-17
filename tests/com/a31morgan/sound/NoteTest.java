package com.a31morgan.sound;

import static org.junit.Assert.*;

import org.junit.Test;

public class NoteTest {
	
	@Test
	public void testConstructor() {
		Note n = new Note(Pitch.C4);
		assertEquals(Note.QUARTER_NOTE, n.getLength());
		assertEquals(Note.NORMAL_ARTICULATION, n.getArticulation());
		assertEquals(Note.NORMAL_VOLUME, n.getEmphasis());
	}

	@Test
	public void testGetDurationMillis() {
		{
			Note note = new Note(Pitch.A3, Note.QUARTER_NOTE);
			assertEquals(600, note.getDurationMillis(100));
		}
		{
			Note note = new Note(Pitch.A3, Note.HALF_NOTE);
			assertEquals(1200, note.getDurationMillis(100));
		}
		{
			Note note = new Note(Pitch.A3, Note.WHOLE_NOTE);
			assertEquals(2400, note.getDurationMillis(100));
		}
		{
			Note note = new Note(Pitch.A3, Note.QUARTER_NOTE);
			assertEquals(1, note.getDurationMillis(60000));
		}
	}
	
	@Test
	public void testDot_ThatItIncreasesDurationBy50Percent() {
		Note note = new Note(Pitch.A1, Note.QUARTER_NOTE);
		assertEquals(600, note.getDurationMillis(100));
		note.dot();
		assertEquals(900, note.getDurationMillis(100));
	}
	
	@Test
	public void testDot_ThatItIncreasesDurationBy225Percent_WhenCalledTwice() { //instead of 75%
		Note note = new Note(Pitch.A1, Note.QUARTER_NOTE);
		assertEquals(600, note.getDurationMillis(100));
		note.dot();
		note.dot();
		assertEquals(1350, note.getDurationMillis(100));
	}
	
	@Test
	public void testAccent_ThatItIncreasesVolumeBy20Percent() {
		Note n = new Note(Pitch.C4, Note.QUARTER_NOTE);
		n.accent();
		assertEquals(1.2, n.getEmphasis().volumeRatio, 0.01);
	}
}
