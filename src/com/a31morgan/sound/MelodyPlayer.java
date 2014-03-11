package com.a31morgan.sound;

import static com.a31morgan.sound.Arpeggiator.UP_DOWN;
import static com.a31morgan.sound.Arpeggio.MAJOR;
import static com.a31morgan.sound.Arpeggio.MINOR;
import static com.a31morgan.sound.Note.Length.QUARTER;

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
        for (int ii = 0; ii < 4; ii++) {
	        melody.addAll(UP_DOWN.getArpeggio(MINOR, Pitch.C3, 3), QUARTER);
	        melody.addAll(UP_DOWN.getArpeggio(MAJOR, Pitch.A3$, 3), QUARTER);
	        melody.addAll(UP_DOWN.getArpeggio(MINOR, Pitch.F2, 3), QUARTER);
        }
    	Filter filter = Filter.createFadeInOutFilter(3, Pitch.SAMPLE_RATE);
    	AudioFormat format = new AudioFormat(Pitch.SAMPLE_RATE, 8, 1, true, true);
        MelodyPlayer.play(melody, format, filter);
    }
}
