package com.a31morgan.sound.demos;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;

import com.a31morgan.sound.Melody;
import com.a31morgan.sound.Note;
import com.a31morgan.sound.Pitch;
import com.a31morgan.sound.player.Filter;
import com.a31morgan.sound.player.IFilter;
import com.a31morgan.sound.player.Player;

public class MarysLittleLamb {

	public static void main(String[] args) throws LineUnavailableException {
		Melody melody = new Melody(129);
		melody.add(new Note(Pitch.E5, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.D5, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.C5, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.D5, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.E5, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.E5, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.E5, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.REST, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.D5, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.D5, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.D5, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.REST, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.E5, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.G5, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.G5, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.REST, Note.QUARTER_NOTE));

		melody.add(new Note(Pitch.E5, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.D5, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.C5, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.D5, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.E5, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.E5, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.E5, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.REST, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.D5, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.D5, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.E5, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.D5, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.C5, Note.QUARTER_NOTE));
		melody.add(new Note(Pitch.REST, Note.WHOLE_NOTE));

        	IFilter[] filters = {
        		Filter.createScaleFilter(0.6),
        		Filter.createFadeInOutFilter(5, Pitch.SAMPLE_RATE),
        	};

    		AudioFormat format = new AudioFormat(Pitch.SAMPLE_RATE, 8, 1, true, true);
        	Player.play(melody, format, filters);
	}
}
