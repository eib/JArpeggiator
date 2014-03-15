package com.a31morgan.sound;

import static org.junit.Assert.*;

import org.junit.Test;

public class NoteTest {

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
	
	
}
