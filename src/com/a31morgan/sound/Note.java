package com.a31morgan.sound;

import java.io.IOException;
import java.io.OutputStream;

public class Note {
	private static final double MILLIS_PER_MIN = 60 * 1000; 

	private Pitch pitch;
	private Length length;
	private double fractionRested = 0.2;
	
	public Note(Pitch pitch, Length length) {
		this.pitch = pitch;
		this.length = length;
	}
	
	public void writeData(int bpm, OutputStream os) throws IOException {
		int totalMillis = this.getDurationMillis(bpm);
		int restMillis = (int)(totalMillis * this.fractionRested);
		byte[] tone = this.pitch.getData(totalMillis - restMillis);
		byte[] rest = Pitch.REST.getData(restMillis);
		os.write(tone);
		os.write(rest);
	}
	
	public int getDurationMillis(double bpm) {
		return (int)(MILLIS_PER_MIN / bpm * this.length.getFraction());
	}

	public enum Length {
		WHOLE(1),
		HALF(0.05),
		TRIPLET(0.333),
		QUARTER(0.25),
		SIXTEENTH(0.125);
		
		private final double fraction; //of a whole note
		
		private Length(double fraction) {
			this.fraction = fraction;
		}

		public double getFraction() {
			return fraction;
		}
	}	
}
