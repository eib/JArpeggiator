package com.a31morgan.sound;

import static com.a31morgan.sound.Arpeggiator.UP_DOWN;
import static com.a31morgan.sound.Arpeggio.MAJOR;
import static com.a31morgan.sound.Arpeggio.MINOR;
import static com.a31morgan.sound.Note.SIXTEENTH_NOTE;
import static com.a31morgan.sound.Note.WHOLE_NOTE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class MelodyPlayer {
    
    public static void play(Melody melody, AudioFormat format, Filter... filters) throws LineUnavailableException {
        SourceDataLine line = AudioSystem.getSourceDataLine(format);
        line.open(format, (int)format.getSampleRate());
        line.start();
        play(line, melody, filters);
        line.drain();
        line.close();
    }
    
    private static void play(SourceDataLine line, Melody melody, Filter... filters) {
		ByteArrayOutputStream os = new FilterOutputStream(filters);
		try {
			melody.writeData(os);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		byte[] data = os.toByteArray();
		line.write(data, 0, data.length);
    }
    
    //http://www.youtube.com/watch?v=eMqsWc8muj8
    public static void main(String[] args) throws LineUnavailableException {
        Melody melody = new Melody(129);
        for (int ii = 0; ii < 1; ii++) {
//	        melody.addAll(UP_DOWN.getArpeggio(MINOR, Pitch.C4, 3), SIXTEENTH_NOTE);
//	        melody.addAll(UP_DOWN.getArpeggio(MAJOR, Pitch.A3$, 3), SIXTEENTH_NOTE);
//	        melody.addAll(UP_DOWN.getArpeggio(MINOR, Pitch.F3, 3), SIXTEENTH_NOTE);
//	        melody.addAll(UP_DOWN.getArpeggio(MINOR, Pitch.C4, 3), SIXTEENTH_NOTE);
//	        melody.addAll(UP_DOWN.getArpeggio(MAJOR, Pitch.A3$, 3), SIXTEENTH_NOTE);
//	        melody.addAll(UP_DOWN.getArpeggio(MINOR, Pitch.F3, 3), SIXTEENTH_NOTE);
//	        melody.addAll(UP_DOWN.getArpeggio(MINOR, Pitch.C4, 3), SIXTEENTH_NOTE);
//	        melody.addAll(UP_DOWN.getArpeggio(MINOR, Pitch.C4, 3), SIXTEENTH_NOTE);
//	        //Chorus Progression:
//	        melody.addAll(UP_DOWN.getArpeggio(MAJOR, Pitch.C4, 3), SIXTEENTH_NOTE);
//	        melody.addAll(UP_DOWN.getArpeggio(MINOR, Pitch.A3, 3), SIXTEENTH_NOTE);
//	        melody.addAll(UP_DOWN.getArpeggio(new Arpeggio(new int[] {5, 4, 3}), Pitch.G3, 3), SIXTEENTH_NOTE); //C Major Chord 2nd Inversion, G (+5) C (+4) E (+3)
//	        melody.addAll(UP_DOWN.getArpeggio(new Arpeggio(new int[] {5, 4, 3}), Pitch.G3, 3), SIXTEENTH_NOTE); //C Major Chord 2nd Inversion, G (+5) C (+4) E (+3)
//	        melody.addAll(UP_DOWN.getArpeggio(MAJOR, Pitch.C4, 3), SIXTEENTH_NOTE);
//	        melody.addAll(UP_DOWN.getArpeggio(MINOR, Pitch.A3, 3), SIXTEENTH_NOTE);
//	        melody.addAll(UP_DOWN.getArpeggio(MAJOR, Pitch.E3, 3), SIXTEENTH_NOTE);
//	        melody.addAll(UP_DOWN.getArpeggio(MAJOR, Pitch.E3, 3), SIXTEENTH_NOTE);
//	        melody.addAll(UP_DOWN.getArpeggio(MINOR, Pitch.F3, 3), SIXTEENTH_NOTE);
//	        melody.addAll(UP_DOWN.getArpeggio(MINOR, Pitch.F3, 3), SIXTEENTH_NOTE);
	        
	        
	        //Stockholm Syndrome Chorus Progression:															
	        melody.addAll(UP.getArpeggio(new Arpeggio(new int[] {4, 3, 5, -8,}), Pitch.F3, 3), SIXTEENTH_NOTE); //These are going to be tricky, it goes back to the second note of the arpeggio
	        melody.addAll(UP.getArpeggio(new Arpeggio(new int[] {4, 3, 5, -8,}), Pitch.E3, 3), SIXTEENTH_NOTE); //A Major Chord 2nd Inversion, E (+5) A (+4) C$(+3)
	        melody.addAll(DOWN.getArpeggio(MINOR, Pitch.D7, 3), SIXTEENTH_NOTE); 
	        melody.addAll(DOWN.getArpeggio(MINOR, Pitch.D6, 3), SIXTEENTH_NOTE);
	        //http://takkaria.org/sheets/muse-ss-chorus1.pdf
	        //http://www.youtube.com/watch?v=GaNYUtoEevo   you're welcome ;)
	        
        }
        melody.addAll(new Note(Pitch.REST, WHOLE_NOTE).times(3));
        
        Filter[] filters = {
        		Filter.createScaleFilter(0.5),
        		Filter.createFadeInOutFilter(3, Pitch.SAMPLE_RATE),
        		Filter.createMultiEchoFilter(new double[] { //in fractions
        			0.5, 0.4, 0.2, 0.1, 0.05,
        		}, new int[] { //frames
        			1500, 3000, 8000, 12000, 14000,
        		}),
        };
    	
    	AudioFormat format = new AudioFormat(Pitch.SAMPLE_RATE, 8, 1, true, true);
        MelodyPlayer.play(melody, format, filters);
    }
}
