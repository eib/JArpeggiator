package com.a31morgan.sound;

import java.util.List;

import com.a31morgan.sound.utils.Lists;

public class Note {	
	private static final double MILLIS_PER_MIN = 60 * 1000;
	
	public static final Length WHOLE_NOTE = new Length(1);
	public static final Length HALF_NOTE = new Length(0.5);
	public static final Length TRIPLET_NOTE = new Length(0.333);
	public static final Length QUARTER_NOTE = new Length(0.25);
	public static final Length EIGHTH_NOTE = new Length(0.125);
	public static final Length SIXTEENTH_NOTE = new Length(0.0625);

	public static final Length BEAT_LENGTH = QUARTER_NOTE; // X/4 time signature

	public static final Articulation NORMAL_ARTICULATION = new Articulation();
	public static final Articulation LEGATO = new Articulation(0.9);
	public static final Articulation STACATTO = new Articulation(0.5);

	public static final Emphasis NORMAL_VOLUME = new Emphasis();
	public static final Emphasis ACCENT = new Emphasis(1.2);
	public static final Emphasis BREVE = new Emphasis(0.8);
	
	public Pitch pitch;
	public Length length;
	public Articulation articulation = NORMAL_ARTICULATION;
	public Emphasis emphasis = NORMAL_VOLUME;
	
	public Note(Pitch pitch) {
		this(pitch, QUARTER_NOTE);
	}
	
	public Note(Pitch pitch, Length length) {
		this.pitch = pitch;
		this.length = length;
	}

	public int getDurationMillis(double bpm) {
		return (int)(MILLIS_PER_MIN / bpm * this.length.fraction / BEAT_LENGTH.fraction);
	}
	
	public List<Note> times(int numRepeats) {
		return Lists.times(this, numRepeats);
	}
	
	public void dot() {
		this.length = new Length(this.length.fraction * 1.5);
	}
	
	public void accent() {
		this.emphasis = ACCENT;
	}
	
	/* Getters and Setters */

	public Pitch getPitch() {
		return pitch;
	}

	public void setPitch(Pitch pitch) {
		this.pitch = pitch;
	}

	public Length getLength() {
		return length;
	}

	public void setLength(Length length) {
		this.length = length;
	}

	public Articulation getArticulation() {
		return articulation;
	}

	public void setArticulation(Articulation articulation) {
		this.articulation = articulation;
	}

	public Emphasis getEmphasis() {
		return emphasis;
	}

	public void setEmphasis(Emphasis emphasis) {
		this.emphasis = emphasis;
	}

	@Override
	public String toString() {
		return this.pitch.toString();
	}


	/**
	 * How long the note lasts (in fractions of a whole note).
	 * How long it a note is actually played depends on
	 * the time signature, tempo, and articulation.
	 */
	public static class Length {
		public final double fraction; //of a whole note
		
		private Length(double fraction) {
			this.fraction = fraction;
		}
	}
	
	public static class Articulation {
		public static final double NORMAL_ARTICULATION_RATIO = 0.8;
		
		/**
		 * The ratio that the note will be played (vs silent).
		 * Values should be between 0.0 (played the entire duration)
		 * and 1.0 (completely silent for the duration). 
		 */
		public final double durationRatio;
		
		public Articulation() {
			this(NORMAL_ARTICULATION_RATIO);
		}

		public Articulation(double restRatio) {
			this.durationRatio = restRatio;
		}
	}
	
	public static class Emphasis {
		
		/** 
		 * The ratio of this articulation to "normal" notes.
		 * "1.0" is normal, greater than 1.0 is louder, less than 1.0 is quieter.
		 */
		public final double volumeRatio;
		
		public Emphasis() {
			this(1.0);
		}

		public Emphasis(double volumeRatio) {
			this.volumeRatio = volumeRatio;
		}
	}
}
