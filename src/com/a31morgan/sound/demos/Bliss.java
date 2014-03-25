package com.a31morgan.sound.demos;

import static com.a31morgan.sound.Arpeggiator.UP_DOWN;
import static com.a31morgan.sound.Arpeggio.MAJOR;
import static com.a31morgan.sound.Arpeggio.MINOR;
import static com.a31morgan.sound.Note.SIXTEENTH_NOTE;
import static com.a31morgan.sound.Note.WHOLE_NOTE;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;

import com.a31morgan.sound.Arpeggio;
import com.a31morgan.sound.Melody;
import com.a31morgan.sound.Note;
import com.a31morgan.sound.Pitch;
import com.a31morgan.sound.player.EchoFilter;
import com.a31morgan.sound.player.Filter;
import com.a31morgan.sound.player.IFilter;
import com.a31morgan.sound.player.Player;

public class Bliss {
    
    //http://www.youtube.com/watch?v=eMqsWc8muj8
    public static void main(String[] args) throws LineUnavailableException {
        Melody melody = new Melody(129);
        for (int ii = 0; ii < 1; ii++) {
	        melody.addAll(UP_DOWN.listArpeggio(MINOR, Pitch.C4, 3), SIXTEENTH_NOTE);
	        melody.addAll(UP_DOWN.listArpeggio(MAJOR, Pitch.A3$, 3), SIXTEENTH_NOTE);
	        melody.addAll(UP_DOWN.listArpeggio(MINOR, Pitch.F3, 3), SIXTEENTH_NOTE);
	        melody.addAll(UP_DOWN.listArpeggio(MINOR, Pitch.C4, 3), SIXTEENTH_NOTE);
	        melody.addAll(UP_DOWN.listArpeggio(MAJOR, Pitch.A3$, 3), SIXTEENTH_NOTE);
	        melody.addAll(UP_DOWN.listArpeggio(MINOR, Pitch.F3, 3), SIXTEENTH_NOTE);
	        melody.addAll(UP_DOWN.listArpeggio(MINOR, Pitch.C4, 3), SIXTEENTH_NOTE);
	        melody.addAll(UP_DOWN.listArpeggio(MINOR, Pitch.C4, 3), SIXTEENTH_NOTE);
	        
	        //Chorus Progression:
	        melody.addAll(UP_DOWN.listArpeggio(MAJOR, Pitch.C4, 3), SIXTEENTH_NOTE);
	        melody.addAll(UP_DOWN.listArpeggio(MINOR, Pitch.A3, 3), SIXTEENTH_NOTE);
	        melody.addAll(UP_DOWN.listArpeggio(new Arpeggio(new int[] {5, 4, 3}), Pitch.G3, 3), SIXTEENTH_NOTE); //C Major Chord 2nd Inversion, G (+5) C (+4) E (+3)
	        melody.addAll(UP_DOWN.listArpeggio(new Arpeggio(new int[] {5, 4, 3}), Pitch.G3, 3), SIXTEENTH_NOTE); //C Major Chord 2nd Inversion, G (+5) C (+4) E (+3)
	        melody.addAll(UP_DOWN.listArpeggio(MAJOR, Pitch.C4, 3), SIXTEENTH_NOTE);
	        melody.addAll(UP_DOWN.listArpeggio(MINOR, Pitch.A3, 3), SIXTEENTH_NOTE);
	        melody.addAll(UP_DOWN.listArpeggio(MAJOR, Pitch.E3, 3), SIXTEENTH_NOTE);
	        melody.addAll(UP_DOWN.listArpeggio(MAJOR, Pitch.E3, 3), SIXTEENTH_NOTE);
	        melody.addAll(UP_DOWN.listArpeggio(MINOR, Pitch.F3, 3), SIXTEENTH_NOTE);
	        melody.addAll(UP_DOWN.listArpeggio(MINOR, Pitch.F3, 3), SIXTEENTH_NOTE);
        }
        melody.addAll(new Note(Pitch.REST, WHOLE_NOTE).times(2));
        
        IFilter[] filters = {
        		Filter.createScaleFilter(0.6),
        		Filter.createFadeInOutFilter(5, Pitch.SAMPLE_RATE),
        		EchoFilter.createMultiEchoFilter(new double[] { //in fractions
        			0.5, 0.4, 0.2, 0.1, 0.05,
        		}, new int[] { //frames
        			1500, 3000, 8000, 12000, 14000,
        		}),
        };
    	
    	AudioFormat format = new AudioFormat(Pitch.SAMPLE_RATE, 8, 1, true, true);
        Player.play(melody, format, filters);
    }
}
