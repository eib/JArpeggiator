package com.a31morgan.sound.demos;

import static com.a31morgan.sound.Arpeggiator.DOWN;
import static com.a31morgan.sound.Arpeggiator.UP;
import static com.a31morgan.sound.Arpeggio.MINOR;
import static com.a31morgan.sound.Note.SIXTEENTH_NOTE;
import static com.a31morgan.sound.Note.WHOLE_NOTE;

import javax.sound.sampled.AudioFormat;

import com.a31morgan.sound.Arpeggio;
import com.a31morgan.sound.Melody;
import com.a31morgan.sound.Note;
import com.a31morgan.sound.Pitch;
import com.a31morgan.sound.player.EchoFilter;
import com.a31morgan.sound.player.Filter;
import com.a31morgan.sound.player.IFilter;
import com.a31morgan.sound.player.Player;

public class StockholmSyndrome {

	//http://www.youtube.com/watch?v=GaNYUtoEevo
	//http://takkaria.org/sheets/muse-ss-chorus1.pdf
	public static void main(String[] args) throws Exception {
        Melody melody = new Melody(129);
        melody.addAll(UP.listArpeggio(new Arpeggio(new int[] {4, 3, 5, -8,}), Pitch.F3, 3), SIXTEENTH_NOTE); //These are going to be tricky, it goes back to the second note of the arpeggio
        melody.addAll(UP.listArpeggio(new Arpeggio(new int[] {4, 3, 5, -8,}), Pitch.E3, 3), SIXTEENTH_NOTE); //A Major Chord 2nd Inversion, E (+5) A (+4) C$(+3)
        melody.addAll(DOWN.listArpeggio(MINOR, Pitch.D7, 3), SIXTEENTH_NOTE); 
        melody.addAll(DOWN.listArpeggio(MINOR, Pitch.D6, 3), SIXTEENTH_NOTE);
        melody.addAll(new Note(Pitch.REST, WHOLE_NOTE).times(2));

        IFilter[] filters = {
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
