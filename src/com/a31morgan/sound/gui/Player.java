package com.a31morgan.sound.gui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import com.a31morgan.sound.Filter;
import com.a31morgan.sound.FilterOutputStream;
import com.a31morgan.sound.Melody;
import com.a31morgan.sound.Pitch;

public class Player implements IPlayer {
	private Filter[] filters;
	private SourceDataLine line;
	private AudioFormat format;

	public Player(AudioFormat format) throws LineUnavailableException {
		this.line = AudioSystem.getSourceDataLine(format);
		this.format = format;
		this.filters = new Filter[] {
        		Filter.createScaleFilter(0.5),
        		Filter.createFadeInOutFilter(3, Pitch.SAMPLE_RATE),
        		Filter.createMultiEchoFilter(new double[] { //in fractions
        			0.5, 0.4, 0.2, 0.1, 0.05,
        		}, new int[] { //frames
        			1500, 3000, 8000, 12000, 14000,
        		}),
		};
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
		ByteArrayOutputStream os = new FilterOutputStream(this.filters);
		try {
			melody.writeData(os);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		byte[] data = os.toByteArray();
		this.line.write(data, 0, data.length);
		this.line.drain();
	}

	@Override
	public void pause() {
		line.close();
	}
	
	@Override
	public void stop() {
		line.close();
		line = null;
	}
}