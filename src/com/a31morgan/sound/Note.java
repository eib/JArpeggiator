package com.a31morgan.sound;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class Note {
	public static final Length WHOLE_NOTE = new Length(1);
	public static final Length HALF_NOTE = new Length(0.5);
	public static final Length TRIPLET_NOTE = new Length(0.333);
	public static final Length QUARTER_NOTE = new Length(0.25);
	public static final Length EIGHTH_NOTE = new Length(0.125);
	public static final Length SIXTEENTH_NOTE = new Length(0.0625);
	
	private static final double MILLIS_PER_MIN = 60 * 1000; 

	private Pitch pitch;
	private Length length;
	private double fractionRested = 0.2; //stacatto == higher, legato == smaller
	
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
		return (int)(MILLIS_PER_MIN / bpm * this.length.fraction / QUARTER_NOTE.fraction); // 4/4 Time Signature
	}
	
	public List<Note> times(int numRepeats) {
		return Lists.times(this, numRepeats);
	}

	@Override
	public String toString() {
		return this.pitch.toString();
	}

	public static class Length {
		public double fraction; //of a whole note
		
		private Length(double fraction) {
			this.fraction = fraction;
		}
		
		public Length dot() {
			return new Length(this.fraction * 1.5);
		}
	}
}
