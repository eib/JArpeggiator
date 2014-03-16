package com.a31morgan.sound.gui;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import com.a31morgan.sound.Pitch;

public class KeyboardLayout {

	private static final Map<Integer, Pitch> isomorphicLayout;
	
	static {
		isomorphicLayout = new HashMap<>();
		isomorphicLayout.put(KeyEvent.VK_Z, Pitch.B2);
		isomorphicLayout.put(KeyEvent.VK_X, Pitch.C3$);
		isomorphicLayout.put(KeyEvent.VK_C, Pitch.D3$);
		isomorphicLayout.put(KeyEvent.VK_V, Pitch.F3);
		isomorphicLayout.put(KeyEvent.VK_B, Pitch.G3);
		isomorphicLayout.put(KeyEvent.VK_N, Pitch.A3);
		isomorphicLayout.put(KeyEvent.VK_M, Pitch.B3);
		isomorphicLayout.put(KeyEvent.VK_COMMA, Pitch.C4$);
		isomorphicLayout.put(KeyEvent.VK_PERIOD, Pitch.D4$);
		isomorphicLayout.put(KeyEvent.VK_SLASH, Pitch.F4);
		isomorphicLayout.put(KeyEvent.VK_A, Pitch.E3);
		isomorphicLayout.put(KeyEvent.VK_S, Pitch.F3$);
		isomorphicLayout.put(KeyEvent.VK_D, Pitch.G3$);
		isomorphicLayout.put(KeyEvent.VK_F, Pitch.A3$);
		isomorphicLayout.put(KeyEvent.VK_G, Pitch.C4);
		isomorphicLayout.put(KeyEvent.VK_H, Pitch.D4);
		isomorphicLayout.put(KeyEvent.VK_J, Pitch.E4);
		isomorphicLayout.put(KeyEvent.VK_K, Pitch.F4$);
		isomorphicLayout.put(KeyEvent.VK_L, Pitch.G4$);
		isomorphicLayout.put(KeyEvent.VK_SEMICOLON, Pitch.A4$);
		isomorphicLayout.put(KeyEvent.VK_QUOTE, Pitch.C5);
		isomorphicLayout.put(KeyEvent.VK_Q, Pitch.A3$);
		isomorphicLayout.put(KeyEvent.VK_W, Pitch.B3);
		isomorphicLayout.put(KeyEvent.VK_E, Pitch.C4$);
		isomorphicLayout.put(KeyEvent.VK_R, Pitch.D4$);
		isomorphicLayout.put(KeyEvent.VK_T, Pitch.F4);
		isomorphicLayout.put(KeyEvent.VK_Y, Pitch.G4);
		isomorphicLayout.put(KeyEvent.VK_U, Pitch.A4);
		isomorphicLayout.put(KeyEvent.VK_I, Pitch.B4);
		isomorphicLayout.put(KeyEvent.VK_O, Pitch.C5$);
		isomorphicLayout.put(KeyEvent.VK_P, Pitch.D5$);
		isomorphicLayout.put(KeyEvent.VK_OPEN_BRACKET, Pitch.F5);
	}
	
	public static Pitch keyToPitch(KeyEvent e) {
		Pitch newPitch = isomorphicLayout.get(e.getKeyCode());
		if (newPitch == null) {
			newPitch = Pitch.REST;
		}
		return newPitch;
	}
}
