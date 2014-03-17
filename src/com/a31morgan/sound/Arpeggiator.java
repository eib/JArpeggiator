package com.a31morgan.sound;

import java.util.ArrayList;
import java.util.List;

public enum Arpeggiator {
	UP,
	DOWN {
		@Override
		public List<Pitch> getNotes(List<Pitch> notes) {
			return Lists.reverse(notes);
		}
	},
	UP_DOWN {
		@Override
		public List<Pitch> getNotes(List<Pitch> notes) {
	        if (notes.size() > 1) {
	        	List<Pitch> down = Lists.reverse(notes).subList(1, notes.size() - 1); //excludes low pitch (starting note)
	        	notes.addAll(down);
	        }
	        return notes;
		}
	},
	DOWN_UP {
		@Override
		public List<Pitch> getNotes(List<Pitch> notes) {
			if (notes.size() > 1) {
				List<Pitch> down = Lists.reverse(notes); //includes low pitch (at the end)
				List<Pitch> up = notes.subList(1, notes.size() - 1); //excludes low pitch (at the beginning) and high pitch (at the end)
				notes = new ArrayList<Pitch>();
				notes.addAll(down);
				notes.addAll(up);
			}
			return notes;
		}
	};

	public List<Pitch> getNotes(List<Pitch> notes) {
		return notes;
	}
	
	public List<Pitch> getArpeggio(Arpeggio arpeggio, Pitch start, int numOctaves) {
		return getNotes(arpeggio.getNotes(start, numOctaves));
	}
}
