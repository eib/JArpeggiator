package com.a31morgan.sound.player;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import com.a31morgan.sound.Melody;
import com.a31morgan.sound.Note;
import com.a31morgan.sound.Pitch;
import com.a31morgan.sound.utils.ArrayUtils;

public class Player implements IPlayer {
    
	private IFilter[] filters;
	private SourceDataLine line;
	private AudioFormat format;

	public Player(AudioFormat format, IFilter... filters) throws LineUnavailableException {
		this.line = AudioSystem.getSourceDataLine(format);
		this.format = format;
		this.filters = filters;
	}
    
    public static void play(Melody melody, AudioFormat format, IFilter... filters) throws LineUnavailableException {
    	try (CloseablePlayer player = new CloseablePlayer(format, filters)) {
    		player.play(melody);
    	}
    }

	public void writeData(Melody melody, FilterOutputStream os) {
		for (Note note : melody.getNotes()) {
			writeData(note, melody.getBpm(), os);
		}
	}
	
	public void writeData(Note note, int bpm, FilterOutputStream os) {
		int totalMillis = note.getDurationMillis(bpm);
		int restMillis = (int)(totalMillis * (1.0 - note.articulation.durationRatio));
		double[] tone = getData(note.pitch, totalMillis - restMillis);
		double[] rest = getRestData(restMillis);
		os.write(tone);
		os.write(rest);
	}

    public double[] getData(Pitch pitch, int millis) {
    	if (pitch.isRest()) {
    		return getRestData(millis);
    	}
        int length = Pitch.SAMPLE_RATE * millis / 1000;
    	double sineLength = pitch.getSineLength(Pitch.SAMPLE_RATE);
        double[] sine = new double[(int)sineLength * 1000];
        for (int ii = 0; ii < sine.length; ii++) {
            double angle = 2.0 * Math.PI * ii / sineLength;
            sine[ii] = Math.sin(angle);
        }
    	return ArrayUtils.repeatToLength(sine, length);
    }
    
    public double[] getRestData(int millis) {
        int length = Pitch.SAMPLE_RATE * millis / 1000;
    	return new double[length];
    }

	@Override
	public void start() {
		try {
			this.line.open(this.format, (int)format.getSampleRate());
			this.line.start();
		} catch (LineUnavailableException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void play(Melody melody) {
		FilterOutputStream os = new FilterOutputStream(this.filters);
		writeData(melody, os);
		byte[] data = os.toByteArray();
		this.line.flush();
		this.line.write(data, 0, data.length);
		this.line.drain();
	}

	@Override
	public void pause() {
		line.stop();
	}
	
	@Override
	public void stop() {
		line.close();
		line = null;
	}
}

class CloseablePlayer extends Player implements AutoCloseable {
	public CloseablePlayer(AudioFormat format, IFilter... filters) throws LineUnavailableException {
		super(format, filters);
		this.start();
	}

	@Override
	public void close() {
		this.stop();
	}
}