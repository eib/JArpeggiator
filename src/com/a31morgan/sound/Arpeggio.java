package com.a31morgan.sound;

import java.util.ArrayList;
import java.util.List;


public enum Arpeggio {
	MAJOR(new int[] { 4, 3, 5 }),
	MINOR(new int[] { 3, 4, 5 }),
	DIMINISHED(new int[] { 3, 3, 4, 2 }),
	AUGMENTED(new int[] { 4, 4, 4 });
	
	private int[] intervals;
	
	private Arpeggio(int[] intervals) {
		this.intervals = intervals;
	}
	
    public List<Pitch> getNotes(Pitch start, int numOctaves) {
    	return getNotes(start, numOctaves, this.intervals);
    }
    
    public static List<Pitch> getNotes(Pitch start, int numOctaves, int[] intervals) {
        List<Pitch> notes = new ArrayList<Pitch>();
        int ordinal = start.ordinal();
        int ii = 0;
        do {
            notes.add(Pitch.values()[ordinal]);
            int interval = intervals[ii];
            ii = (ii + 1) % intervals.length;
            ordinal += interval;
        } while (ordinal < Pitch.values().length);
        
        int maxNotes = numOctaves * intervals.length;
        notes = notes.subList(0, Math.min(maxNotes, notes.size()));
        return notes;
    }
}
